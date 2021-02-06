package de.lukaspanni.opensourcestats.client;

import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.ApolloQueryCall;
import com.apollographql.apollo.api.Error;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import org.jetbrains.annotations.NotNull;

import de.lukaspanni.opensourcestats.ApplicationConfig;
import de.lukaspanni.opensourcestats.RepositoryDataQuery;
import de.lukaspanni.opensourcestats.UserContributionsQuery;
import de.lukaspanni.opensourcestats.auth.AuthenticationHandler;
import de.lukaspanni.opensourcestats.auth.GHAuthInterceptor;
import de.lukaspanni.opensourcestats.data.RepositoryDataResponse;
import de.lukaspanni.opensourcestats.data.UserContributionsResponse;
import de.lukaspanni.opensourcestats.type.CustomType;
import de.lukaspanni.opensourcestats.data.RepositoryName;
import de.lukaspanni.opensourcestats.data.TimeSpan;
import okhttp3.OkHttpClient;

public class GithubOAuthClient implements RepositoryDataClient, UserContributionsClient {

    private AuthenticationHandler handler;

    public GithubOAuthClient(AuthenticationHandler handler) {
        this.handler = handler;
    }

    @Override
    public void loadRepositoryData(RepositoryName repository, ClientDataCallback callback) {
        handler.performActionWithToken((accessToken, idToken, ex) -> {
            if (ex != null) {
                return;
            }
            ApolloQueryCall.Factory graphqlClient = getGraphqlClient(accessToken);
            graphqlClient.query(new RepositoryDataQuery(repository.getName(), repository.getOwner())).enqueue(
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
        handler.performActionWithToken((accessToken, idToken, ex) -> {
            if (ex != null) {
                return;
            }
            ApolloQueryCall.Factory graphqlClient = getGraphqlClient(accessToken);
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

    protected ApolloQueryCall.Factory getGraphqlClient(String accessToken){
        return getGraphqlClientInternal(accessToken);
    }

    private ApolloQueryCall.Factory getGraphqlClientInternal(String accessToken) {
        OkHttpClient httpClient = (new OkHttpClient.Builder())
                .addInterceptor(new GHAuthInterceptor(accessToken))
                .build();

        return ApolloClient.builder()
                .serverUrl(ApplicationConfig.getApiEndpoint())
                .okHttpClient(httpClient)
                .addCustomTypeAdapter(CustomType.DATETIME, new DateCustomTypeAdapter())
                .build();
    }
}
