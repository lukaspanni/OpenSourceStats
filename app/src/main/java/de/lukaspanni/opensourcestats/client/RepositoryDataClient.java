package de.lukaspanni.opensourcestats.client;

public interface RepositoryDataClient {
    void repositoryData(String repository, String owner, ClientDataCallback callback);
}
