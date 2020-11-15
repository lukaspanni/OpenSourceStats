package de.lukaspanni.opensourcestats.repository;

import java.util.Date;

import de.lukaspanni.opensourcestats.client.ClientDataCallback;
import de.lukaspanni.opensourcestats.client.ClientDataCallbackDecorator;
import de.lukaspanni.opensourcestats.client.GHClient;
import de.lukaspanni.opensourcestats.data.UserContributionsResponse;
import de.lukaspanni.opensourcestats.repository.cache.ResponseCache;
import de.lukaspanni.opensourcestats.util.TimeSpan;
import de.lukaspanni.opensourcestats.util.TimeSpanFactory;

public class UserContributionsRepository extends Repository implements UserContributionsDataStore {

    private ResponseCache<TimeSpan, UserContributionsResponse> cache;
    //TODO: Extract client interface
    private GHClient client;

    public UserContributionsRepository(GHClient client)
    {
        this.client = client;
        this.cache = new ResponseCache<>();
    }

    public UserContributionsRepository(GHClient client, int maxAge) {
        this.client = client;
        this.cache = new ResponseCache<>(maxAge);
    }

    private void getData(TimeSpan timeSpan, ClientDataCallback callback, boolean forceReload) {
        if (!forceReload) {
            UserContributionsResponse data = cache.get(timeSpan);
            if (data != null) {
                callback.callback(data);
                return;
            }
        }
        //Wrap callback to add response to local cache
        callback = new ClientDataCallbackDecorator(callback, responseData -> cache.put(timeSpan, (UserContributionsResponse) responseData));
        client.loadUserContributionsData(timeSpan, callback);
    }


    @Override
    public void userContributionsLastWeek(ClientDataCallback callback, boolean forceReload) {
        getData(TimeSpanFactory.getLastWeek(), callback, forceReload);
    }

    @Override
    public void userContributionsCurrentWeek(ClientDataCallback callback, boolean forceReload) {
        getData(TimeSpanFactory.getCurrentWeek(), callback, forceReload);
    }

    @Override
    public void userContributionsWeek(Date dayInWeek, ClientDataCallback callback, boolean forceReload) {
        getData(TimeSpanFactory.getWeek(dayInWeek), callback, forceReload);
    }

    @Override
    public void userContributionsLastMonth(ClientDataCallback callback, boolean forceReload) {
        getData(TimeSpanFactory.getLastMonth(), callback, forceReload);
    }

    @Override
    public void userContributionsCurrentMonth(ClientDataCallback callback, boolean forceReload) {
        getData(TimeSpanFactory.getCurrentMonth(), callback, forceReload);
    }

    @Override
    public void userContributionsMonth(Date dayInMonth, ClientDataCallback callback, boolean forceReload) {
        getData(TimeSpanFactory.getMonth(dayInMonth), callback, forceReload);
    }
}
