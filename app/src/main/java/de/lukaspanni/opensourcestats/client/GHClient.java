package de.lukaspanni.opensourcestats.client;

import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Error;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import de.lukaspanni.opensourcestats.RepositoryDataQuery;
import de.lukaspanni.opensourcestats.auth.AuthHandler;
import de.lukaspanni.opensourcestats.auth.GHAuthInterceptor;
import de.lukaspanni.opensourcestats.UserContributionsQuery;
import de.lukaspanni.opensourcestats.data.RepositoryDataResponse;
import de.lukaspanni.opensourcestats.data.UserContributionsResponse;
import de.lukaspanni.opensourcestats.type.CustomType;

import org.jetbrains.annotations.NotNull;


import de.lukaspanni.opensourcestats.util.TimeSpan;
import okhttp3.OkHttpClient;

public class GHClient implements RepositoryDataClient, UserContributionsClient {

    private final String API_ENDPOINT = "https://api.github.com/graphql";
    private AuthHandler handler;

    public GHClient(AuthHandler handler) {
        this.handler = handler;
    }

    public void repositoryData(String repository, String owner, ClientDataCallback callback) {
        handler.getAuthState().performActionWithFreshTokens(handler.getAuthService(), (accessToken, idToken, ex) -> {
            if (ex != null) {
                return;
            }
            ApolloClient graphqlClient = getGraphqlClient(accessToken);
            graphqlClient.query(new RepositoryDataQuery(repository, owner)).enqueue(
                    new ApolloCall.Callback<RepositoryDataQuery.Data>() {
                        @Override
                        public void onResponse(@NotNull Response<RepositoryDataQuery.Data> response) {
                            if (response.getData() != null) {
                                callback.callback(new RepositoryDataResponse(response.getData().repository()));
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


    @Override
    public void loadUserContributionsData(TimeSpan timeSpan, ClientDataCallback clientDataCallback) {
        handler.getAuthState().performActionWithFreshTokens(handler.getAuthService(), (accessToken, idToken, ex) -> {
            if (ex != null) {
                return;
            }
            ApolloClient graphqlClient = getGraphqlClient(accessToken);
            graphqlClient.query(new UserContributionsQuery(timeSpan.getStart(), timeSpan.getEnd())).enqueue(
                    new ApolloCall.Callback<UserContributionsQuery.Data>() {
                        @Override
                        public void onResponse(@NotNull Response<UserContributionsQuery.Data> response) {
                            if (response.getData() != null) {
                                UserContributionsResponse data = new UserContributionsResponse(response.getData().viewer());
                                if (clientDataCallback != null) {
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

    private ApolloClient getGraphqlClient(String accessToken) {
        OkHttpClient httpClient = (new OkHttpClient.Builder())
                .addInterceptor(new GHAuthInterceptor(accessToken))
                .build();

        return ApolloClient.builder()
                .serverUrl(API_ENDPOINT)
                .okHttpClient(httpClient)
                .addCustomTypeAdapter(CustomType.DATETIME, new DateCustomTypeAdapter())
                .build();
    }
}
