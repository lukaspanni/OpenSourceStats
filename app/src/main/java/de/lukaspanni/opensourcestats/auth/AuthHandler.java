package de.lukaspanni.opensourcestats.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

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

    private AuthHandlerActivity authHandlerActivity;
    private AuthState authState;
    private AuthorizationService authService;

    //TODO: Remove URL-Dependencies -> Config
    private final String AUTH_ENDPOINT = "https://github.com/login/oauth/authorize";
    private final String TOKEN_ENDPOINT = "https://github.com/login/oauth/access_token";
    private final Uri REDIRECT_URI = Uri.parse("de.lukaspanni.oss://opensourcestats/auth");
    private final String SCOPES = "repo:status";
    private final String CLIENT_ID;
    public final static int REQUEST_CODE = 42;


    public static AuthHandler getInstance(AuthHandlerActivity activity) {
        if (instance != null) {
            instance.setAuthHandlerActivity(activity);
            return instance;
        }
        instance = new AuthHandler(activity);
        return instance;
    }

    public void setAuthHandlerActivity(AuthHandlerActivity activity){
        if(authHandlerActivity != activity){
            authHandlerActivity = activity;
            authState = null;
            if(authService != null)
                authService.dispose();
            authService = null;
        }
    }

    private AuthHandler(AuthHandlerActivity activity) {
        this.authHandlerActivity = activity;
        //Not Optimal TODO: find better Solution
        CLIENT_ID = activity.getClientId();
    }

    public boolean checkAuth() {
        authState = getAuthState();
        return authState.isAuthorized();
    }

    public AuthState getAuthState() {
        if (authState == null)
            authState = readAuthState();
        return authState;
    }

    public AuthorizationService getAuthService() {
        if (authService != null) {
            return authService;
        }
        authService = new AuthorizationService(authHandlerActivity.getActivity());
        return authService;
    }


    public void authenticate() {
        AuthorizationServiceConfiguration serviceConfig = new AuthorizationServiceConfiguration(
                Uri.parse(AUTH_ENDPOINT), Uri.parse(TOKEN_ENDPOINT));
        authState = new AuthState(serviceConfig);

        AuthorizationRequest.Builder requestBuilder = new AuthorizationRequest.Builder(serviceConfig, CLIENT_ID, ResponseTypeValues.CODE, REDIRECT_URI);
        AuthorizationRequest request = requestBuilder.setScope(SCOPES).build();

        authService = new AuthorizationService(authHandlerActivity.getActivity());
        Intent authIntent = authService.getAuthorizationRequestIntent(request);
        authHandlerActivity.getActivity().startActivityForResult(authIntent, REQUEST_CODE);
    }

    public void writeAuthState() {
        SharedPreferences authPreferences = authHandlerActivity.getAuthPreferences();
        authPreferences.edit().putString("authState", authState.jsonSerializeString()).apply();
    }

    //Bad Testability because of AuthState.jsonDeserialize (uses URI.parse internally)
    private AuthState readAuthState() {
        SharedPreferences authPreferences = authHandlerActivity.getAuthPreferences();
        String stateString = authPreferences.getString("authState", null);
        if (stateString != null) {
            try {
                return AuthState.jsonDeserialize(stateString);
            } catch (JSONException e) {
                Log.e("Error Loading State", e.getLocalizedMessage());
            }
        }
        return new AuthState();
    }

    @Override
    protected void finalize() throws Throwable {
        if(this.authService != null){
            authService.dispose();
        }
    }
}
