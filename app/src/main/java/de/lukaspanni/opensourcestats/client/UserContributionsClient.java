package de.lukaspanni.opensourcestats.client;

import de.lukaspanni.opensourcestats.util.TimeSpan;

public interface UserContributionsClient {
    void loadUserContributionsData(TimeSpan timeSpan, ClientDataCallback clientDataCallback);
}
