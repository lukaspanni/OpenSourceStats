package de.lukaspanni.opensourcestats.client;

import java.util.Date;

public interface Client {

    void userContributionsLastWeek(ClientDataCallback callback, boolean forceReload);

    void userContributionsCurrentWeek(ClientDataCallback callback, boolean forceReload);

    void userContributionsWeek(Date dayInWeek, ClientDataCallback callback, boolean forceReload);

    void userContributionsLastMonth(ClientDataCallback callback, boolean forceReload);

    void userContributionsCurrentMonth(ClientDataCallback callback, boolean forceReload);

    void userContributionsMonth(Date dayInMonth, ClientDataCallback callback, boolean forceReload);
}
