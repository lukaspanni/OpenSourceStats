package com.example.opensoucestats;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


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

public class MainActivity extends AppCompatActivity {

    private AuthorizationService authService;

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

        authenticate();
    }

    private void authenticate() {
        final String CLIENT_ID = getString(R.string.client_id);
        final Uri REDIRECT_URI = Uri.parse("de.lukaspanni.oss://opensourcestats/auth");
        AuthorizationServiceConfiguration serviceConfig = new AuthorizationServiceConfiguration(
                Uri.parse("https://github.com/login/oauth/authorize"), // authorization endpoint
                Uri.parse("https://github.com/login/oauth/access_token")); // token endpoint

        AuthorizationRequest.Builder requestBuilder = new AuthorizationRequest.Builder(serviceConfig, CLIENT_ID, ResponseTypeValues.CODE, REDIRECT_URI);

        AuthorizationRequest request = requestBuilder.setScope("repo:status").build();

        authService = new AuthorizationService(this);

        Intent authIntent = authService.getAuthorizationRequestIntent(request);
        startActivityForResult(authIntent, 42);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 42) {
            AuthorizationResponse resp = AuthorizationResponse.fromIntent(data);
            AuthorizationException ex = AuthorizationException.fromIntent(data);
            if(resp != null){
                final Context ctx = this;
                ClientAuthentication clientAuth = new ClientSecretPost(getString(R.string.client_secret));
                TokenRequest req = resp.createTokenExchangeRequest();
                authService.performTokenRequest(req, clientAuth, new AuthorizationService.TokenResponseCallback() {
                    @Override
                    public void onTokenRequestCompleted(
                            TokenResponse response, AuthorizationException ex) {
                        if (response != null) {
                            Toast.makeText(ctx, response.accessToken, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ctx, ex.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });


            }else{
                Toast.makeText(this, "FAILED - " + ex.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}