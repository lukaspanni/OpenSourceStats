package de.lukaspanni.opensourcestats.mock;

import de.lukaspanni.opensourcestats.client.ClientDataCallback;
import de.lukaspanni.opensourcestats.client.UserContributionsClient;
import de.lukaspanni.opensourcestats.util.TimeSpan;

public class FakeUserContributionsClient extends FakeRepositoryClientBase implements UserContributionsClient {

    private TimeSpan calledTimeSpan;
    public TimeSpan getCalledTimeSpan() {
        return calledTimeSpan;
    }


    @Override
    public void loadUserContributionsData(TimeSpan timeSpan, ClientDataCallback clientDataCallback) {
        this.setCalledCallback(clientDataCallback);
        this.calledTimeSpan = timeSpan;
    }
}
