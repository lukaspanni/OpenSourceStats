package com.example.opensoucestats;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.CustomTypeAdapter;
import com.apollographql.apollo.api.CustomTypeValue;
import com.apollographql.apollo.api.Error;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.opensoucestats.auth.AuthActivity;
import com.example.opensoucestats.auth.AuthHandler;
import com.example.opensourcestats.LoginQuery;
import com.example.opensourcestats.UserContributionsQuery;
import com.example.opensourcestats.type.CustomType;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import net.openid.appauth.AuthState;
import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationResponse;
import net.openid.appauth.AuthorizationService;
import net.openid.appauth.ClientAuthentication;
import net.openid.appauth.ClientSecretPost;
import net.openid.appauth.TokenRequest;
import net.openid.appauth.TokenResponse;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity {

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
        if(getIntent().getAction() == null){
            return;
        }
        //TODO: start with AuthActivity -> less activity switches
        AuthHandler handler = AuthHandler.getInstance(this);
        if(!handler.checkAuth()){
            Intent authIntent = new Intent(this, AuthActivity.class);
            startActivity(authIntent);
        }

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

    /* private void useToken() {
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
    } */
}