package de.lukaspanni.opensourcestats.repository;


import org.jetbrains.annotations.NotNull;

import de.lukaspanni.opensourcestats.client.ClientDataCallback;
import de.lukaspanni.opensourcestats.client.ClientDataCallbackDecorator;
import de.lukaspanni.opensourcestats.client.RepositoryDataClient;
import de.lukaspanni.opensourcestats.data.RepositoryDataResponse;
import de.lukaspanni.opensourcestats.repository.cache.ResponseCache;
import de.lukaspanni.opensourcestats.util.RepositoryName;
import de.lukaspanni.opensourcestats.util.TimeSpan;

//better name?
public class RepositoryDataRepository extends Repository<RepositoryName>{

    private ResponseCache<RepositoryName, RepositoryDataResponse> cache;
    private RepositoryDataClient client;

    public RepositoryDataRepository(ResponseCache<RepositoryName, RepositoryDataResponse> cache, RepositoryDataClient client){
        this.cache = cache;
        this.client = client;
    }

    public RepositoryDataRepository(RepositoryDataClient client) {
        this(new ResponseCache<>(), client);
    }

    public RepositoryDataRepository(RepositoryDataClient client, int maxAge) {
        this(new ResponseCache<>(maxAge), client);
    }

    public void loadRepositoryData(RepositoryName repository, ClientDataCallback callback, boolean forceReload){
        if(!forceReload){
            RepositoryDataResponse data = cache.get(repository);
            if(data != null){
                callback.callback(data);
                return;
            }
        }
        ClientDataCallback decoratedCallback = new ClientDataCallbackDecorator(callback, getAddToCacheCallback(repository));
        client.repositoryData(repository, decoratedCallback);
    }

    @NotNull
    @Override
    protected ClientDataCallback getAddToCacheCallback(RepositoryName repository) {
        return response -> cache.put(repository, (RepositoryDataResponse) response);
    }

}
