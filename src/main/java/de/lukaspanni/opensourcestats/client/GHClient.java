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

import java.util.Calendar;
import java.util.Date;

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
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        cal.add(Calendar.WEEK_OF_YEAR, -1);
        Date weekStart = cal.getTime();
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEnd = cal.getTime();
        loadUserContributionsData(new TimeSpan(weekStart, weekEnd), callback);
    }

    @Override
    public void userContributionsCurrentWeek(ClientDataCallback callback) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        Date weekStart = cal.getTime();
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEnd = cal.getTime();
        loadUserContributionsData(new TimeSpan(weekStart, weekEnd), callback);
    }

    @Override
    public void userContributionsWeek(Date dayInWeek, ClientDataCallback callback) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dayInWeek);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        Date weekStart = cal.getTime();
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEnd = cal.getTime();
        loadUserContributionsData(new TimeSpan(weekStart, weekEnd), callback);
    }

    @Override
    public void userContributionsLastMonth(ClientDataCallback callback) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.MONTH, -1);
        Date monthStart = cal.getTime();
        cal.add(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH)-1);
        Date monthEnd = cal.getTime();
        loadUserContributionsData(new TimeSpan(monthStart, monthEnd), callback);    }

    @Override
    public void userContributionsCurrentMonth(ClientDataCallback callback) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date monthStart = cal.getTime();
        cal.add(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH)-1);
        Date monthEnd = cal.getTime();
        loadUserContributionsData(new TimeSpan(monthStart, monthEnd), callback);
    }

    @Override
    public void userContributionsMonth(Date dayInMonth, ClientDataCallback callback) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dayInMonth);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date monthStart = cal.getTime();
        cal.add(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH)-1);
        Date monthEnd = cal.getTime();
        loadUserContributionsData(new TimeSpan(monthStart, monthEnd), callback);
    }
}
