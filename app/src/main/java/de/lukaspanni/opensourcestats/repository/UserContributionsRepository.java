package de.lukaspanni.opensourcestats.repository;

import org.jetbrains.annotations.NotNull;


import de.lukaspanni.opensourcestats.client.ClientDataCallback;
import de.lukaspanni.opensourcestats.client.ClientDataCallbackDecorator;
import de.lukaspanni.opensourcestats.client.UserContributionsClient;
import de.lukaspanni.opensourcestats.data.UserContributionsResponse;
import de.lukaspanni.opensourcestats.repository.cache.ResponseCache;
import de.lukaspanni.opensourcestats.data.TimeSpan;
import de.lukaspanni.opensourcestats.data.TimeSpanFactory;

public class UserContributionsRepository extends Repository<TimeSpan> {

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

    public void loadUserContributionsInTimeSpan(TimeSpan timeSpan, ClientDataCallback callback, boolean forceReload) {
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


    public void loadUserContributionsInLastWeek(ClientDataCallback callback, boolean forceReload) {
        loadUserContributionsInTimeSpan(TimeSpanFactory.getLastWeek(), callback, forceReload);
    }

    public void loadUserContributionsInCurrentWeek(ClientDataCallback callback, boolean forceReload) {
        loadUserContributionsInTimeSpan(TimeSpanFactory.getCurrentWeek(), callback, forceReload);
    }

    public void loadUserContributionsInLastMonth(ClientDataCallback callback, boolean forceReload) {
        loadUserContributionsInTimeSpan(TimeSpanFactory.getLastMonth(), callback, forceReload);
    }

    public void loadUserContributionsInCurrentMonth(ClientDataCallback callback, boolean forceReload) {
        loadUserContributionsInTimeSpan(TimeSpanFactory.getCurrentMonth(), callback, forceReload);
    }

    @Override
    public void clearCache(){
        this.cache.clearCache();
    }

}
