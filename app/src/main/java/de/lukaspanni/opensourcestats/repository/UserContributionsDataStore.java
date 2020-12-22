package de.lukaspanni.opensourcestats.repository;

import de.lukaspanni.opensourcestats.client.ClientDataCallback;
import de.lukaspanni.opensourcestats.util.TimeSpan;

public interface UserContributionsDataStore {

    void userContributionsLastWeek(ClientDataCallback callback, boolean forceReload);

    void userContributionsCurrentWeek(ClientDataCallback callback, boolean forceReload);

    void userContributionsLastMonth(ClientDataCallback callback, boolean forceReload);

    void userContributionsCurrentMonth(ClientDataCallback callback, boolean forceReload);

    void userContributionsTimeSpan(TimeSpan timeSpan, ClientDataCallback callback, boolean forceReload);
}
