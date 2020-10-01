package com.example.opensoucestats;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.CustomTypeAdapter;
import com.apollographql.apollo.api.CustomTypeValue;
import com.apollographql.apollo.api.Error;
import com.apollographql.apollo.api.Input;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.opensourcestats.LoginQuery;
import com.example.opensourcestats.UserContributionsQuery;
import com.example.opensourcestats.type.CustomType;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import net.openid.appauth.AuthState;
import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationRequest;
import net.openid.appauth.AuthorizationResponse;
import net.openid.appauth.AuthorizationService;
import net.openid.appauth.AuthorizationServiceConfiguration;
import net.openid.appauth.ClientAuthentication;
import net.openid.appauth.ClientSecretBasic;
import net.openid.appauth.ClientSecretPost;
import net.openid.appauth.ResponseTypeValues;
import net.openid.appauth.TokenRequest;
import net.openid.appauth.TokenResponse;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity {

    private AuthorizationService authService;
    private AuthState authState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        authState = this.readAuthState();
        if (!authState.isAuthorized()) {
            authenticate();
        }
    }

    private void authenticate() {
        final String CLIENT_ID = getString(R.string.client_id);
        final Uri REDIRECT_URI = Uri.parse("de.lukaspanni.oss://opensourcestats/auth");
        AuthorizationServiceConfiguration serviceConfig = new AuthorizationServiceConfiguration(
                Uri.parse("https://github.com/login/oauth/authorize"), // authorization endpoint
                Uri.parse("https://github.com/login/oauth/access_token")); // token endpoint

        authState = new AuthState(serviceConfig);

        AuthorizationRequest.Builder requestBuilder = new AuthorizationRequest.Builder(serviceConfig, CLIENT_ID, ResponseTypeValues.CODE, REDIRECT_URI);
        AuthorizationRequest request = requestBuilder.setScope("repo:status").build();

        authService = new AuthorizationService(this);
        Intent authIntent = authService.getAuthorizationRequestIntent(request);
        startActivityForResult(authIntent, 42);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final Context ctx = this;

        if (requestCode == 42) {
            final AuthorizationResponse resp = AuthorizationResponse.fromIntent(data);
            AuthorizationException ex = AuthorizationException.fromIntent(data);
            if (resp != null) {
                ClientAuthentication clientAuth = new ClientSecretPost(getString(R.string.client_secret));
                TokenRequest req = resp.createTokenExchangeRequest();
                authService.performTokenRequest(req, clientAuth, new AuthorizationService.TokenResponseCallback() {
                    @Override
                    public void onTokenRequestCompleted(
                            TokenResponse response, AuthorizationException ex) {

                        authState.update(response, ex);
                        if(authState.isAuthorized()){
                            writeAuthState(authState);
                        }
                        if (response != null) {
                            Toast.makeText(ctx, "SUCCESS", Toast.LENGTH_LONG).show();
                            useToken();
                        } else {
                            Toast.makeText(ctx, ex.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            } else {
                Toast.makeText(this, "FAILED - " + ex.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void writeAuthState(@NonNull AuthState authState){
        SharedPreferences authPreferences = getSharedPreferences("auth", MODE_PRIVATE);
        authPreferences.edit().putString("authState", authState.jsonSerializeString()).apply();
    }

    private AuthState readAuthState(){
        SharedPreferences authPreferences = getSharedPreferences("auth", MODE_PRIVATE);
        String stateString = authPreferences.getString("authState", null);
        if(stateString != null) {
            try {
                return AuthState.jsonDeserialize(stateString);
            } catch (JSONException e) {
                Log.e("Error Loading State", e.getLocalizedMessage());
            }
        }
        return new AuthState();
    }

    class AuthInterceptor implements Interceptor {
        private String token;

        public AuthInterceptor(String token) {
            this.token = token;
        }


        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request req = chain.request().newBuilder().addHeader("Authorization", this.token).build();
            return chain.proceed(req);
        }
    }

    private void useToken() {
        authState.performActionWithFreshTokens(authService, new AuthState.AuthStateAction() {
            @Override
            public void execute(@Nullable String accessToken, @Nullable String idToken, @Nullable AuthorizationException ex) {
                if (ex != null) {
                    return;
                }
                //use Token
                ApolloClient graphqlClient = ApolloClient.builder()
                        .serverUrl("https://api.github.com/graphql")
                        .okHttpClient((new OkHttpClient.Builder())
                                .addInterceptor(new AuthInterceptor("token " + accessToken))
                                .build())
                        .addCustomTypeAdapter(CustomType.DATETIME, new CustomTypeAdapter<Date>() {
                            private DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                            @NotNull
                            @Override
                            public CustomTypeValue<?> encode(Date date) {
                                return new CustomTypeValue.GraphQLString(format.format(date));
                            }

                            @Override
                            public Date decode(@NotNull CustomTypeValue<?> customTypeValue) {
                                try {
                                    return format.parse((String) customTypeValue.value);
                                } catch (ParseException e) {
                                    Log.e("Conversion Error" , e.getLocalizedMessage());
                                    return null;
                                }
                            }
                        })
                        .build();
                graphqlClient.query(new LoginQuery()).enqueue(new ApolloCall.Callback<LoginQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<LoginQuery.Data> response) {
                        if (response.getData() != null) {
                            Log.i("SUCCESS", response.getData().viewer().login());
                        } else {
                            for (Error err : response.getErrors()) {
                                Log.e("API ERROR", err.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        Log.e("ERROR", e.getLocalizedMessage());
                    }
                });
                graphqlClient.query(new UserContributionsQuery(new Date(System.currentTimeMillis() - (7 * 1000 * 60 * 60 * 24)), new Date())).enqueue(new ApolloCall.Callback<UserContributionsQuery.Data>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(@NotNull Response<UserContributionsQuery.Data> response) {
                        if (response.getData() != null) {
                            Log.i("Total Commits", String.valueOf(response.getData().viewer().contributionsCollection().totalCommitContributions()));
                            Log.i("Total Issues", String.valueOf(response.getData().viewer().contributionsCollection().totalIssueContributions()));
                            Log.i("Total Pull-Requests", String.valueOf(response.getData().viewer().contributionsCollection().totalPullRequestContributions()));
                            Log.i("Total Pull-Request Reviews", String.valueOf(response.getData().viewer().contributionsCollection().totalPullRequestReviewContributions()));
                            Set<String> repositories = new HashSet<>();
                            // TODO: Check if better solution exists
                            repositories.addAll(response.getData().viewer().contributionsCollection().commitContributionsByRepository()
                                    .stream()
                                    .map(UserContributionsQuery.CommitContributionsByRepository::repository)
                                    .map(UserContributionsQuery.Repository::nameWithOwner)
                                    .collect(Collectors.toList()));
                            repositories.addAll(response.getData().viewer().contributionsCollection().issueContributionsByRepository()
                                    .stream()
                                    .map(UserContributionsQuery.IssueContributionsByRepository::repository)
                                    .map(UserContributionsQuery.Repository1::nameWithOwner)
                                    .collect(Collectors.toList()));
                            repositories.addAll(response.getData().viewer().contributionsCollection().pullRequestContributionsByRepository()
                                    .stream()
                                    .map(UserContributionsQuery.PullRequestContributionsByRepository::repository)
                                    .map(UserContributionsQuery.Repository2::nameWithOwner)
                                    .collect(Collectors.toList()));
                            repositories.addAll(response.getData().viewer().contributionsCollection().pullRequestReviewContributionsByRepository()
                                    .stream()
                                    .map(UserContributionsQuery.PullRequestReviewContributionsByRepository::repository)
                                    .map(UserContributionsQuery.Repository3::nameWithOwner)
                                    .collect(Collectors.toList()));
                            Log.i("Contributed to Repositories", String.join(", ", repositories));
                        } else {
                            for (Error err : response.getErrors()) {
                                Log.e("API ERROR", err.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        Log.e("ERROR", e.getLocalizedMessage());
                    }
                });
            }
        });
    }
}