package de.lukaspanni.opensourcestats.repository;

import de.lukaspanni.opensourcestats.client.ClientDataCallback;
import de.lukaspanni.opensourcestats.client.ClientDataCallbackDecorator;
import de.lukaspanni.opensourcestats.client.RepositoryDataClient;
import de.lukaspanni.opensourcestats.data.RepositoryDataResponse;
import de.lukaspanni.opensourcestats.repository.cache.ResponseCache;
import de.lukaspanni.opensourcestats.util.RepositoryName;

//TODO: Unit-Test; Break Dependency to Cache -> new Constructor to set Cache
//better name?
public class RepositoryDataRepository extends Repository{

    private ResponseCache<RepositoryName, RepositoryDataResponse> cache;
    private RepositoryDataClient client;

    public RepositoryDataRepository(RepositoryDataClient client) {
        this.cache = new ResponseCache<>();
        this.client = client;
    }

    public RepositoryDataRepository(RepositoryDataClient client, int maxAge) {
        this.cache = new ResponseCache<>(maxAge);
        this.client = client;
    }

    public void repositorySummary(RepositoryName repository, ClientDataCallback callback, boolean forceReload){
        if(!forceReload){
            RepositoryDataResponse data = cache.get(repository);
            if(data != null){
                callback.callback(data);
                return;
            }
        }
        //Wrap callback to add response to cache
        ClientDataCallback decoratedCallback = new ClientDataCallbackDecorator(callback, response ->  cache.put(repository, (RepositoryDataResponse) response));
        client.repositoryData(repository, decoratedCallback);
    }

}
