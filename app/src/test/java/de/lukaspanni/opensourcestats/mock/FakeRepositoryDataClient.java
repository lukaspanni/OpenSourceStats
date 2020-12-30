package de.lukaspanni.opensourcestats.mock;

import de.lukaspanni.opensourcestats.client.ClientDataCallback;
import de.lukaspanni.opensourcestats.client.RepositoryDataClient;
import de.lukaspanni.opensourcestats.util.RepositoryName;

public class FakeRepositoryDataClient extends FakeRepositoryClientBase implements RepositoryDataClient {

    private RepositoryName calledRepositoryName;
    public RepositoryName getCalledRepositoryName() {
        return calledRepositoryName;
    }

    @Override
    public void repositoryData(RepositoryName repository, ClientDataCallback callback) {
        this.setCalledCallback(callback);
        this.calledRepositoryName = repository;
    }
}
