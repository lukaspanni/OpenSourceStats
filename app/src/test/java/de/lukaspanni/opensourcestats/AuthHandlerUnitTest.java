package de.lukaspanni.opensourcestats;

import android.app.Activity;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import org.junit.Test;


import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;

import de.lukaspanni.opensourcestats.auth.AuthHandler;
import de.lukaspanni.opensourcestats.auth.AuthHandlerActivity;

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
        SharedPreferencesMockActivity mockActivity = new SharedPreferencesMockActivity();
        mockActivity.setSharedPreferences(sharedPreferences);
        return AuthHandler.getInstance(new MockAuthHandlerActivity(mockActivity));
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
        assertEquals("{\"scope\":\"repo:status\",\"mLastTokenResponse\":{\"access_token\":\"426faff42050695169f34affeacafecf09efa00a\",\"request\":{\"redirectUri\":\"de.lukaspanni.oss://opensourcestats/auth\",\"clientId\":\"678ef41ce100a242b1ff\",\"configuration\":{\"tokenEndpoint\":\"https://github.com/login/oauth/access_token\",\"authorizationEndpoint\":\"https://github.com/login/oauth/authorize\"},\"authorizationCode\":\"ffff424242f2fb2ffaba\",\"additionalParameters\":{},\"grantType\":\"authorization_code\"},\"additionalParameters\":{},\"token_type\":\"bearer\"},\"config\":{\"tokenEndpoint\":\"https://github.com/login/oauth/access_token\",\"authorizationEndpoint\":\"https://github.com/login/oauth/authorize\"}}", mockEditor.getPutStringContent().getValue());
        assertEquals("authState", mockEditor.getPutStringContent().getKey());
    }

    @Test
    public void test_auth_handler_authenticate() {
        //TODO: Implement
    }


    //TODO: move to separate files
    private static class MockEditor implements SharedPreferences.Editor {
        private boolean applied;
        private AbstractMap.SimpleEntry<String, String> putStringContent;

        public boolean isApplied() {
            return applied;
        }

        public AbstractMap.SimpleEntry<String, String> getPutStringContent() {
            return putStringContent;
        }

        @Override
        public SharedPreferences.Editor putString(String key, @Nullable String value) {
            putStringContent = new AbstractMap.SimpleEntry<>(key, value);
            return this;
        }

        @Override
        public SharedPreferences.Editor putStringSet(String key, @Nullable Set<String> values) {
            return null;
        }

        @Override
        public SharedPreferences.Editor putInt(String key, int value) {
            return null;
        }

        @Override
        public SharedPreferences.Editor putLong(String key, long value) {
            return null;
        }

        @Override
        public SharedPreferences.Editor putFloat(String key, float value) {
            return null;
        }

        @Override
        public SharedPreferences.Editor putBoolean(String key, boolean value) {
            return null;
        }

        @Override
        public SharedPreferences.Editor remove(String key) {
            return null;
        }

        @Override
        public SharedPreferences.Editor clear() {
            return null;
        }

        @Override
        public boolean commit() {
            return false;
        }

        @Override
        public void apply() {
            this.applied = true;
        }
    }

    private class MockAuthHandlerActivity implements AuthHandlerActivity {

        private Activity mockActivity;

        public MockAuthHandlerActivity() {
            this(null);
        }

        public MockAuthHandlerActivity(Activity mockActivity) {
            this.mockActivity = mockActivity;
        }

        @Override
        public String getClientId() {
            //Random Client-ID
            return "678ef41ce100a242b1ff";
        }

        @Override
        public Activity getActivity() {
            return mockActivity;
        }
    }

    private class SharedPreferencesMockActivity extends Activity {
        private SharedPreferences sharedPreferences;

        public void setSharedPreferences(SharedPreferences sharedPreferences) {
            this.sharedPreferences = sharedPreferences;
        }

        @Override
        public SharedPreferences getSharedPreferences(String name, int mode) {
            return sharedPreferences;
        }
    }

    private class SharedPreferencesMock implements SharedPreferences {

        private String getGetStringReturnValue;
        private Editor mockEditor;

        public void setGetStringReturnValue(String getStringReturnValue) {
            this.getGetStringReturnValue = getStringReturnValue;
        }

        public void setMockEditor(Editor mockEditor) {
            this.mockEditor = mockEditor;
        }

        @Override
        public Map<String, ?> getAll() {
            return null;
        }

        @Nullable
        @Override
        public String getString(String key, @Nullable String defValue) {
            return getGetStringReturnValue;
        }

        @Nullable
        @Override
        public Set<String> getStringSet(String key, @Nullable Set<String> defValues) {
            return null;
        }

        @Override
        public int getInt(String key, int defValue) {
            return 0;
        }

        @Override
        public long getLong(String key, long defValue) {
            return 0;
        }

        @Override
        public float getFloat(String key, float defValue) {
            return 0;
        }

        @Override
        public boolean getBoolean(String key, boolean defValue) {
            return false;
        }

        @Override
        public boolean contains(String key) {
            return false;
        }

        @Override
        public Editor edit() {
            return mockEditor;
        }

        @Override
        public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {

        }

        @Override
        public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {

        }
    }

}