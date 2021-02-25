package de.lukaspanni.opensourcestats.mock;


import java.util.ArrayList;

import de.lukaspanni.opensourcestats.client.ClientDataCallback;
import de.lukaspanni.opensourcestats.data.TimeSpan;
import de.lukaspanni.opensourcestats.data.UserContributionsResponse;
import de.lukaspanni.opensourcestats.repository.UserContributionsRepository;

public class UserContributionsRepositoryFake extends UserContributionsRepository {

    private ArrayList<UserContributionsResponse> fakeResponses = null;
    private int position;

    public UserContributionsRepositoryFake(){
        super(null, null);
    }

    public UserContributionsRepositoryFake(ArrayList<UserContributionsResponse> responses){
        super(null, null);
        fakeResponses = responses;
        position = 0;
    }

    @Override
    public void loadUserContributionsInTimeSpan(TimeSpan timeSpan, ClientDataCallback callback, boolean forceReload) {
        if(fakeResponses == null) return;
        UserContributionsResponse fakeResponse = fakeResponses.get(position++ % fakeResponses.size());
        callback.callback(fakeResponse);
    }
}
