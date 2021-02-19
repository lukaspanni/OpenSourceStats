package de.lukaspanni.opensourcestats.repository;

import org.jetbrains.annotations.NotNull;

import de.lukaspanni.opensourcestats.client.ClientDataCallback;
import de.lukaspanni.opensourcestats.repository.cache.CacheKey;

// Is this class really needed
public abstract class Repository<T extends CacheKey> {
    @NotNull
    protected abstract ClientDataCallback getAddToCacheCallback(T key);

    public abstract void clearCache();
}
