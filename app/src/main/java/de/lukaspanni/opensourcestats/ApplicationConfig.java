package de.lukaspanni.opensourcestats;

import android.net.Uri;

public class ApplicationConfig {

    private static final String API_ENDPOINT = "https://api.github.com/graphql";
    public static final String AUTH_STATE_SHARED_PREFERENCES_KEY = "authState";
    private static final Uri AUTH_ENDPOINT = Uri.parse("https://github.com/login/oauth/authorize");
    private static final Uri TOKEN_ENDPOINT = Uri.parse("https://github.com/login/oauth/access_token");
    private static final String SCOPES = "repo:status";
    private static final Uri REDIRECT_URI = Uri.parse("de.lukaspanni.oss://opensourcestats/auth");
    private static final int REQUEST_CODE = 42;


    public static Uri getAuthEndpoint() {
        return AUTH_ENDPOINT;
    }

    public static Uri getTokenEndpoint() {
        return TOKEN_ENDPOINT;
    }

    public static String getScopes() {
        return SCOPES;
    }

    public static Uri getRedirectUri() {
        return REDIRECT_URI;
    }

    public static int getRequestCode() {
        return REQUEST_CODE;
    }

    public static String getApiEndpoint() {
        return API_ENDPOINT;
    }

    public static String getAuthStateSharedPreferencesKey() {
        return AUTH_STATE_SHARED_PREFERENCES_KEY;
    }
}
