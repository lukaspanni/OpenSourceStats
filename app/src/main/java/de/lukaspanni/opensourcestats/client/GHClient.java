package de.lukaspanni.opensourcestats.client;

import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Error;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import de.lukaspanni.opensourcestats.auth.AuthHandler;
import de.lukaspanni.opensourcestats.auth.GHAuthInterceptor;
import de.lukaspanni.opensourcestats.UserContributionsQuery;
import de.lukaspanni.opensourcestats.type.CustomType;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

import de.lukaspanni.opensourcestats.util.DateUtility;
import de.lukaspanni.opensourcestats.util.TimeSpan;
import okhttp3.OkHttpClient;

public class GHClient implements Client {

    private final String API_ENDPOINT = "https://api.github.com/graphql";
    private AuthHandler handler;
    private ResponseCache cache = ResponseCache.getInstance();

    public GHClient(AuthHandler handler) {
        this.handler = handler;
    }

    private void loadUserContributionsData(TimeSpan timeSpan, ClientDataCallback clientDataCallback) {
        ResponseData data = cache.get(timeSpan);
        if (data != null) {
            clientDataCallback.callback(data);
            return;
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

            graphqlClient.query(new UserContributionsQuery(timeSpan.getStart(), timeSpan.getEnd())).enqueue(
                    new ApolloCall.Callback<UserContributionsQuery.Data>() {
                        @Override
                        public void onResponse(@NotNull Response<UserContributionsQuery.Data> response) {
                            if (response.getData() != null) {
                                UserContributionsResponse data = new UserContributionsResponse(response.getData().viewer());
                                cache.put(timeSpan, data);
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

    @Override
    public void userContributionsLastWeek(ClientDataCallback callback) {
        loadUserContributionsData(DateUtility.getLastWeek(), callback);
    }

    @Override
    public void userContributionsCurrentWeek(ClientDataCallback callback) {
        loadUserContributionsData(DateUtility.getCurrentWeek(), callback);
    }

    @Override
    public void userContributionsWeek(Date dayInWeek, ClientDataCallback callback) {
        loadUserContributionsData(DateUtility.getWeek(dayInWeek), callback);
    }

    @Override
    public void userContributionsLastMonth(ClientDataCallback callback) {
        loadUserContributionsData(DateUtility.getLastMonth(), callback);    }

    @Override
    public void userContributionsCurrentMonth(ClientDataCallback callback) {
        loadUserContributionsData(DateUtility.getCurrentMonth(), callback);
    }

    @Override
    public void userContributionsMonth(Date dayInMonth, ClientDataCallback callback) {
        loadUserContributionsData(DateUtility.getMonth(dayInMonth), callback);
    }
}