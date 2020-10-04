package de.lukaspanni.opensourcestats.client;

import java.util.Date;

public interface Client {
    void userContributionsLastWeek(ClientDataCallback callback);
    void userContributionsCurrentWeek(ClientDataCallback callback);
    void userContributionsWeek(Date dayInWeek, ClientDataCallback callback);
    void userContributionsLastMonth(ClientDataCallback callback);
    void userContributionsCurrentMonth(ClientDataCallback callback);
    void userContributionsMonth(Date dayInMonth, ClientDataCallback callback);
}
