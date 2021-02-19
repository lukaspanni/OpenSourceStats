package de.lukaspanni.opensourcestats.repository.cache;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.lukaspanni.opensourcestats.data.ResponseData;

/**
 * ResponseCache, used to cache server-responses
 */
public class ResponseCache<K extends CacheKey, T extends ResponseData> {

    private Map<Long, CacheEntry<T>> dataStore = new HashMap<>();
    private long maxAge; // in seconds
    private static long DEFAULT_MAXAGE = 3600;
    private int hits = 0;
    private int misses = 0;

    public ResponseCache() {
        this(DEFAULT_MAXAGE);
    }

    public ResponseCache(long pMaxAge) {
        if(pMaxAge <= 0) pMaxAge = DEFAULT_MAXAGE;
        this.maxAge = pMaxAge;
    }

    public void put(K key, T data) {
        this.dataStore.put(key.getKey(), new CacheEntry<>(data));
    }

    public T get(K key) {
        CacheEntry<T> entry = this.dataStore.get(key.getKey());
        if (entry == null || entry.getAge() + this.maxAge < (new Date()).getTime() / 1000) {
            this.misses++;
            return null;
        }
        this.hits++;
        return entry.getData();
    }

    public void clearCache(){
        dataStore.clear();
        hits = 0;
        misses = 0;
    }

    //Functions for Statistics & Debugging

    public int getHits() {
        return this.hits;
    }

    public int getMisses() {
        return this.misses;
    }


}

