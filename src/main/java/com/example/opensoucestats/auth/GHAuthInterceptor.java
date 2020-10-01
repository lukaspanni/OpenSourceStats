package com.example.opensoucestats.auth;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;

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
