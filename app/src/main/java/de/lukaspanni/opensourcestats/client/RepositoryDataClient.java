package de.lukaspanni.opensourcestats.client;

import de.lukaspanni.opensourcestats.data.RepositoryName;

public interface RepositoryDataClient {
    void repositoryData(RepositoryName repository, ClientDataCallback callback);
}
