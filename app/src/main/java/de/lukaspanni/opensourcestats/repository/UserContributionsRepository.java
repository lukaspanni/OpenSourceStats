package de.lukaspanni.opensourcestats.repository;

import de.lukaspanni.opensourcestats.client.ClientDataCallback;
import de.lukaspanni.opensourcestats.client.ClientDataCallbackDecorator;
import de.lukaspanni.opensourcestats.client.UserContributionsClient;
import de.lukaspanni.opensourcestats.data.UserContributionsResponse;
import de.lukaspanni.opensourcestats.repository.cache.ResponseCache;
import de.lukaspanni.opensourcestats.util.TimeSpan;
import de.lukaspanni.opensourcestats.util.TimeSpanFactory;

public class UserContributionsRepository extends Repository implements UserContributionsDataStore {

    private ResponseCache<TimeSpan, UserContributionsResponse> cache;
    private UserContributionsClient client;

    public UserContributionsRepository(UserContributionsClient client)
    {
        this.client = client;
        this.cache = new ResponseCache<>();
    }

    public UserContributionsRepository(UserContributionsClient client, int maxAge) {
        this.client = client;
        this.cache = new ResponseCache<>(maxAge);
    }

    public void userContributionsTimeSpan(TimeSpan timeSpan, ClientDataCallback callback, boolean forceReload) {
        if (!forceReload) {
            UserContributionsResponse data = cache.get(timeSpan);
            if (data != null) {
                callback.callback(data);
                return;
            }
        }
        //Wrap callback to add response to local cache
        ClientDataCallback decoratedCallback = new ClientDataCallbackDecorator(callback, responseData -> cache.put(timeSpan, (UserContributionsResponse) responseData));
        client.loadUserContributionsData(timeSpan, decoratedCallback);
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
