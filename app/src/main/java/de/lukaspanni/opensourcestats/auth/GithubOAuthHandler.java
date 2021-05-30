package de.lukaspanni.opensourcestats.auth;

import android.content.Intent;
import android.content.SharedPreferences;

import net.openid.appauth.AuthState;
import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationRequest;
import net.openid.appauth.AuthorizationService;
import net.openid.appauth.AuthorizationServiceConfiguration;
import net.openid.appauth.ResponseTypeValues;
import net.openid.appauth.TokenResponse;

import org.json.JSONException;

import de.lukaspanni.opensourcestats.ApplicationConfig;

/**
 * AuthHandler, needed to authenticate via OAuth
 */
public class GithubOAuthHandler implements AuthenticationHandler {


    private final String CLIENT_ID;
    private AuthHandlerActivity authHandlerActivity;
    private AuthState authState;
    private AuthorizationService authService;


    public GithubOAuthHandler(AuthHandlerActivity activity) {
        this.authHandlerActivity = activity;
        //Not Optimal TODO: find better Solution
        CLIENT_ID = activity.getClientId();
    }

    @Override
    public boolean checkAuth() {
        authState = getAuthState();
        return authState.isAuthorized();
    }

    @Override
    public void performActionWithToken(AuthenticatedAction action) {
        //Uses own Interface AuthenticatedAction to reduce dependency to openid AppAuth
        //Makes it easier to use different/multiple forms of authentication
        authState.performActionWithFreshTokens(getAuthService(), action::execute);
    }


    @Override
    public void authenticate() {
        AuthorizationServiceConfiguration serviceConfig = new AuthorizationServiceConfiguration(
                ApplicationConfig.getAuthEndpoint(), ApplicationConfig.getTokenEndpoint());
        authState = new AuthState(serviceConfig);

        AuthorizationRequest.Builder requestBuilder = new AuthorizationRequest.Builder(serviceConfig, CLIENT_ID, ResponseTypeValues.CODE, ApplicationConfig.getRedirectUri());
        AuthorizationRequest request = requestBuilder.setScope(ApplicationConfig.getScopes()).build();

        authService = new AuthorizationService(authHandlerActivity.getActivity());
        Intent authIntent = authService.getAuthorizationRequestIntent(request);
        authHandlerActivity.getActivity().startActivityForResult(authIntent, ApplicationConfig.getRequestCode());
    }

    public void setAuthHandlerActivity(AuthHandlerActivity activity) {
        if (authHandlerActivity != activity) {
            authHandlerActivity = activity;
            authState = null;
            if (authService != null)
                authService.dispose();
            authService = null;
        }
    }

    private AuthState getAuthState() {
        if (authState == null)
            authState = readAuthState();
        return authState;
    }

    public void updateState(TokenResponse response, AuthorizationException ex1) {
        getAuthState().update(response, ex1);
        writeAuthState();
    }

    public AuthorizationService getAuthService() {
        if (authService != null) {
            return authService;
        }
        authService = new AuthorizationService(authHandlerActivity.getActivity());
        return authService;
    }


    public void forceWriteAuthState() {
        getAuthState();
        this.writeAuthState();
    }


    private void writeAuthState() {
        if (authState != null) {
            SharedPreferences authPreferences = authHandlerActivity.getAuthPreferences();
            authPreferences.edit().putString(ApplicationConfig.getAuthStateSharedPreferencesKey(), authState.jsonSerializeString()).apply();
        }
    }

    //Bad Testability because of AuthState.jsonDeserialize (uses URI.parse internally)
    private AuthState readAuthState() {
        SharedPreferences authPreferences = authHandlerActivity.getAuthPreferences();
        String stateString = authPreferences.getString(ApplicationConfig.getAuthStateSharedPreferencesKey(), null);
        if (stateString != null) {
            try {
                return AuthState.jsonDeserialize(stateString);
            } catch (JSONException e) {
                //Log.e("Error Loading State", e.getLocalizedMessage());
            }
        }
        return new AuthState();
    }

    @Override
    protected void finalize() throws Throwable {
        if (this.authService != null) {
            authService.dispose();
        }
    }
}
