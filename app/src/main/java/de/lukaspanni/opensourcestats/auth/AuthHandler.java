package de.lukaspanni.opensourcestats.auth;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import com.lukaspanni.opensourcestats.R;

import net.openid.appauth.AuthState;
import net.openid.appauth.AuthorizationRequest;
import net.openid.appauth.AuthorizationService;
import net.openid.appauth.AuthorizationServiceConfiguration;
import net.openid.appauth.ResponseTypeValues;

import org.json.JSONException;

import static android.content.Context.MODE_PRIVATE;

/**
 * AuthHandler, needed to authenticate via OAuth
 */
public class AuthHandler {

    private static AuthHandler instance;

    private Activity activity;
    private AuthState authState;
    private AuthorizationService authService;
    private final String AUTH_ENDPOINT = "https://github.com/login/oauth/authorize";
    private final String TOKEN_ENDPOINT = "https://github.com/login/oauth/access_token";
    private final String CLIENT_ID;
    private final Uri REDIRECT_URI = Uri.parse("de.lukaspanni.oss://opensourcestats/auth");
    private final String SCOPES = "repo:status";
    public final static int REQUEST_CODE = 42;


    public static AuthHandler getInstance(Activity activity){
        if(instance != null){
            instance.activity = activity;
            return instance;
        }
        instance = new AuthHandler(activity);
        return instance;
    }

    private AuthHandler(Activity activity) {
        this.activity = activity;
        //Not Optimal
        CLIENT_ID = activity.getString(R.string.client_id);
    }

    public boolean checkAuth(){
        authState = readAuthState();
        return authState.isAuthorized();
    }

    public AuthState getAuthState(){
        if(authState != null){
            return authState;
        }
        return readAuthState();
    }

    public AuthorizationService getAuthService(){
        if(authService != null){
            return authService;
        }
        authService = new AuthorizationService(activity);
        return authService;
    }


    public void authenticate() {
        AuthorizationServiceConfiguration serviceConfig = new AuthorizationServiceConfiguration(
                Uri.parse(AUTH_ENDPOINT), Uri.parse(TOKEN_ENDPOINT));
        authState = new AuthState(serviceConfig);

        AuthorizationRequest.Builder requestBuilder = new AuthorizationRequest.Builder(serviceConfig, CLIENT_ID, ResponseTypeValues.CODE, REDIRECT_URI);
        AuthorizationRequest request = requestBuilder.setScope(SCOPES).build();

        authService = new AuthorizationService(activity);
        //TODO: Evaluate using performAuthorizationRequest
        Intent authIntent = authService.getAuthorizationRequestIntent(request);
        activity.startActivityForResult(authIntent, REQUEST_CODE);
    }

    public void writeAuthState(){
        SharedPreferences authPreferences = activity.getSharedPreferences("auth", MODE_PRIVATE);
        authPreferences.edit().putString("authState", authState.jsonSerializeString()).apply();
    }

    private AuthState readAuthState(){
        SharedPreferences authPreferences = activity.getSharedPreferences("auth", MODE_PRIVATE);
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

}
