package com.example.opensoucestats.client;

import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Error;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.example.opensoucestats.auth.AuthHandler;
import com.example.opensoucestats.auth.GHAuthInterceptor;
import com.example.opensourcestats.UserContributionsQuery;
import com.example.opensourcestats.type.CustomType;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.HashMap;

import okhttp3.OkHttpClient;

public class GHClient {

    private final String API_ENDPOINT = "https://api.github.com/graphql";
    private AuthHandler handler;
    private ResponseCache cache = new ResponseCache();

    public GHClient(AuthHandler handler) {
        this.handler = handler;
    }

    public void loadData(Date start, Date end, ClientDataCallback clientDataCallback) {
        TimeSpan timeSpan = new TimeSpan(start, end);
        ResponseData data = cache.get(timeSpan);
        if(data != null){
            clientDataCallback.callback(data);
        }
        handler.getAuthState().performActionWithFreshTokens(handler.getAuthService(), (accessToken, idToken, ex) -> {
            if (ex != null) {
                return;
            }
            OkHttpClient httpClient = (new OkHttpClient.Builder())
                    .addInterceptor(new GHAuthInterceptor(accessToken))
                    .build();

            ApolloClient graphqlClient = ApolloClient.builder()
                    .serverUrl(API_ENDPOINT)
                    .okHttpClient(httpClient)
                    .addCustomTypeAdapter(CustomType.DATETIME, new DateCustomTypeAdapter())
                    .build();

            graphqlClient.query(new UserContributionsQuery(start, end)).enqueue(
                    new ApolloCall.Callback<UserContributionsQuery.Data>() {
                        @Override
                        public void onResponse(@NotNull Response<UserContributionsQuery.Data> response) {
                            if (response.getData() != null) {
                                UserContributionsResponse data = new UserContributionsResponse(response.getData().viewer());
                                cache.put(timeSpan, data);
                                if(clientDataCallback != null) {
                                    clientDataCallback.callback(data);
                                }
                            } else {
                                for (Error err : response.getErrors()) {
                                    Log.e("API ERROR", err.getMessage());
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NotNull ApolloException e) {
                            Log.e("ERROR", e.getLocalizedMessage());
                        }
                    });
        });
    }

}
