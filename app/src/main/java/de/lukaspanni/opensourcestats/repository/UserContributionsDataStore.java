package de.lukaspanni.opensourcestats.repository;

import java.util.Date;

import de.lukaspanni.opensourcestats.client.ClientDataCallback;

public interface UserContributionsDataStore {

    void userContributionsLastWeek(ClientDataCallback callback, boolean forceReload);

    void userContributionsCurrentWeek(ClientDataCallback callback, boolean forceReload);

    void userContributionsWeek(Date dayInWeek, ClientDataCallback callback, boolean forceReload);

    void userContributionsLastMonth(ClientDataCallback callback, boolean forceReload);

    void userContributionsCurrentMonth(ClientDataCallback callback, boolean forceReload);

    void userContributionsMonth(Date dayInMonth, ClientDataCallback callback, boolean forceReload);
}
