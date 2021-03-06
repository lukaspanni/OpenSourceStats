package de.lukaspanni.opensourcestats.auth;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * GHAuthInterceptor, used to add the authentication token to every request
 */
public class GHAuthInterceptor implements Interceptor {
    private String tokenString;

    public GHAuthInterceptor(String token) {
        this.tokenString = "token " + token;
    }

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request req = chain.request().newBuilder().addHeader("Authorization", this.tokenString).build();
        return chain.proceed(req);
    }
}
