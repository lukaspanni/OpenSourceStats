package de.lukaspanni.opensourcestats.repository;

import org.jetbrains.annotations.NotNull;


import java.util.Calendar;

import de.lukaspanni.opensourcestats.client.ClientDataCallback;
import de.lukaspanni.opensourcestats.client.ClientDataCallbackDecorator;
import de.lukaspanni.opensourcestats.client.UserContributionsClient;
import de.lukaspanni.opensourcestats.data.UserContributionsResponse;
import de.lukaspanni.opensourcestats.repository.cache.ResponseCache;
import de.lukaspanni.opensourcestats.util.TimeSpan;
import de.lukaspanni.opensourcestats.util.TimeSpanFactory;

public class UserContributionsRepository extends Repository<TimeSpan> implements UserContributionsDataStore {

    private ResponseCache<TimeSpan, UserContributionsResponse> cache;
    private UserContributionsClient client;

    public UserContributionsRepository(ResponseCache<TimeSpan, UserContributionsResponse> cache, UserContributionsClient client){
        this.cache = cache;
        this.client = client;
    }

    public UserContributionsRepository(UserContributionsClient client) {
        this(new ResponseCache<>(), client);
    }

    public UserContributionsRepository(UserContributionsClient client, int maxAge) {
        this(new ResponseCache<>(maxAge), client);
    }

    public void userContributionsTimeSpan(TimeSpan timeSpan, ClientDataCallback callback, boolean forceReload) {
        if (!forceReload) {
            UserContributionsResponse data = cache.get(timeSpan);
            if (data != null) {
                callback.callback(data);
                return;
            }
        }
        ClientDataCallback decoratedCallback = new ClientDataCallbackDecorator(callback, getAddToCacheCallback(timeSpan));
        client.loadUserContributionsData(timeSpan, decoratedCallback);
    }

    @NotNull
    @Override
    protected ClientDataCallback getAddToCacheCallback(TimeSpan timeSpan) {
        return responseData -> cache.put(timeSpan, (UserContributionsResponse) responseData);
    }


    @Override
    public void userContributionsLastWeek(ClientDataCallback callback, boolean forceReload) {
        userContributionsTimeSpan(TimeSpanFactory.getLastWeek(), callback, forceReload);
    }

    @Override
    public void userContributionsCurrentWeek(ClientDataCallback callback, boolean forceReload) {
        userContributionsTimeSpan(TimeSpanFactory.getCurrentWeek(), callback, forceReload);
    }

    @Override
    public void userContributionsLastMonth(ClientDataCallback callback, boolean forceReload) {
        userContributionsTimeSpan(TimeSpanFactory.getLastMonth(), callback, forceReload);
    }

    @Override
    public void userContributionsCurrentMonth(ClientDataCallback callback, boolean forceReload) {
        userContributionsTimeSpan(TimeSpanFactory.getCurrentMonth(), callback, forceReload);
    }

}
