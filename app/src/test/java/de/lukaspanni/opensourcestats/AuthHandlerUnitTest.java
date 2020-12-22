package de.lukaspanni.opensourcestats;

import android.content.SharedPreferences;

import org.junit.Test;


import de.lukaspanni.opensourcestats.auth.AuthHandler;
import de.lukaspanni.opensourcestats.mock.MockAuthHandlerActivity;
import de.lukaspanni.opensourcestats.mock.MockEditor;
import de.lukaspanni.opensourcestats.mock.SharedPreferencesMock;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class AuthHandlerUnitTest {

    public static final String AUTH_STATE_JSON = "{\"scope\":\"repo:status\",\"config\":{\"authorizationEndpoint\":\"https://github.com/login/oauth/authorize\",\"tokenEndpoint\":\"https://github.com/login/oauth/access_token\"},\"mLastTokenResponse\":{\"request\":{\"configuration\":{\"authorizationEndpoint\":\"https://github.com/login/oauth/authorize\",\"tokenEndpoint\":\"https://github.com/login/oauth/access_token\"},\"clientId\":\"678ef41ce100a242b1ff\",\"grantType\":\"authorization_code\",\"redirectUri\":\"de.lukaspanni.oss://opensourcestats/auth\",\"authorizationCode\":\"ffff424242f2fb2ffaba\",\"additionalParameters\":{}},\"token_type\":\"bearer\",\"access_token\":\"426faff42050695169f34affeacafecf09efa00a\",\"scope\":\"repo:status\",\"additionalParameters\":{}}}";

    private AuthHandler getAuthHandlerWithSharedPreferences(String str) {
        return getAuthHandlerWithSharedPreferences(str, null);
    }

    private AuthHandler getAuthHandlerWithSharedPreferences(String str, SharedPreferences.Editor mockEditor) {
        SharedPreferencesMock sharedPreferences = new SharedPreferencesMock();
        sharedPreferences.setGetStringReturnValue(str);
        sharedPreferences.setMockEditor(mockEditor);
        return AuthHandler.getInstance(new MockAuthHandlerActivity(sharedPreferences));
    }

    @Test
    public void test_auth_handler_checkAuth_authenticated() {
        //Bad Testability of AuthHandler due to use of SharedPreferences
        AuthHandler handler = getAuthHandlerWithSharedPreferences(AUTH_STATE_JSON);

        boolean authenticated = handler.checkAuth();
        assertTrue(authenticated);
    }

    @Test
    public void test_auth_handler_checkAuth_not_authenticated() {
        AuthHandler handler = getAuthHandlerWithSharedPreferences(null);

        boolean authenticated = handler.checkAuth();
        assertFalse(authenticated);
    }

    @Test
    public void test_auth_handler_write_AuthState() {
        MockEditor mockEditor = new MockEditor();
        AuthHandler handler = getAuthHandlerWithSharedPreferences(AUTH_STATE_JSON, mockEditor);

        handler.getAuthState();
        handler.writeAuthState();
        assertTrue(mockEditor.isApplied());
        assertThat(mockEditor.getPutStringContent().getValue(), is(equalTo("{\"scope\":\"repo:status\",\"mLastTokenResponse\":{\"access_token\":\"426faff42050695169f34affeacafecf09efa00a\",\"request\":{\"redirectUri\":\"de.lukaspanni.oss://opensourcestats/auth\",\"clientId\":\"678ef41ce100a242b1ff\",\"configuration\":{\"tokenEndpoint\":\"https://github.com/login/oauth/access_token\",\"authorizationEndpoint\":\"https://github.com/login/oauth/authorize\"},\"authorizationCode\":\"ffff424242f2fb2ffaba\",\"additionalParameters\":{},\"grantType\":\"authorization_code\"},\"additionalParameters\":{},\"token_type\":\"bearer\"},\"config\":{\"tokenEndpoint\":\"https://github.com/login/oauth/access_token\",\"authorizationEndpoint\":\"https://github.com/login/oauth/authorize\"}}")));
        assertThat(mockEditor.getPutStringContent().getKey(), is(equalTo("authState")));
    }

    @Test
    public void test_auth_handler_authenticate() {
        //TODO: Implement
        //Not really testable,  needs more work
    }


}