package de.lukaspanni.opensourcestats.client;

import de.lukaspanni.opensourcestats.data.RepositoryName;

public interface RepositoryDataClient {
    void loadRepositoryData(RepositoryName repository, ClientDataCallback callback);
}
