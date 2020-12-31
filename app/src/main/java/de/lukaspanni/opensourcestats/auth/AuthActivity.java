package de.lukaspanni.opensourcestats.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import de.lukaspanni.opensourcestats.MainActivity;
import de.lukaspanni.opensourcestats.OpenSourceStatsApplication;

import com.lukaspanni.opensourcestats.R;

import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationResponse;
import net.openid.appauth.ClientAuthentication;
import net.openid.appauth.ClientSecretPost;
import net.openid.appauth.TokenRequest;

public class AuthActivity extends AppCompatActivity implements AuthHandlerActivity {

    private AuthHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OpenSourceStatsApplication app = (OpenSourceStatsApplication) getApplication();
        handler = app.getAuthHandler(this);
        setContentView(R.layout.activity_auth);
        Button auth_button = (Button) findViewById(R.id.auth_button);
        auth_button.setOnClickListener(v -> {
            handler.authenticate();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final Context ctx = this;

        if (requestCode == AuthHandler.REQUEST_CODE) {
            final AuthorizationResponse resp = AuthorizationResponse.fromIntent(data);
            AuthorizationException ex = AuthorizationException.fromIntent(data);
            if (resp != null) {
                ClientAuthentication clientAuth = new ClientSecretPost(getString(R.string.client_secret));
                TokenRequest req = resp.createTokenExchangeRequest();
                handler.getAuthService().performTokenRequest(req, clientAuth, (response, ex1) -> {

                    handler.updateState(response, ex1);
                    if (response != null) {
                        Toast.makeText(ctx, getString(R.string.auth_success_text), Toast.LENGTH_LONG).show();
                        Log.i("AUTH", "User authenticated");
                        Intent toMainIntent = new Intent(this, MainActivity.class);
                        startActivity(toMainIntent);
                    } else {
                        Toast.makeText(ctx, getString(R.string.auth_failed_text), Toast.LENGTH_LONG).show();
                        Log.e("AUTH", ex1.getLocalizedMessage());
                    }
                });
            } else {
                Toast.makeText(ctx, getString(R.string.auth_failed_text), Toast.LENGTH_LONG).show();
                Log.e("AUTH",  ex.getLocalizedMessage());
            }
        }
    }

    @Override
    public String getClientId() {
        return getString(R.string.client_id);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public SharedPreferences getAuthPreferences() {
        return getSharedPreferences("auth", MODE_PRIVATE);
    }
}