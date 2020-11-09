package de.lukaspanni.opensourcestats.client.cache;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.lukaspanni.opensourcestats.data.ResponseData;
import de.lukaspanni.opensourcestats.util.TimeSpan;

/**
 * ResponseCache, used to cache server-responses
 */
public class ResponseCache {

    private Map<TimeSpan, CacheEntry> dataStore = new HashMap<>();
    private long maxAge; // in seconds
    private static long DEFAULT_MAXAGE = 3600;
    private int hits = 0;
    private int misses = 0;

    private static ResponseCache instance;

    public static ResponseCache getInstance() {
        return getInstance(DEFAULT_MAXAGE);
    }

    public static ResponseCache getInstance(long maxAge) {
        if(instance == null){
            instance = new ResponseCache(maxAge);
        }
        instance.maxAge = maxAge;
        return instance;
    }


    private ResponseCache(long maxAge) {
        this.maxAge = maxAge;
    }

    public void put(TimeSpan ts, ResponseData data) {
        this.dataStore.put(ts, new CacheEntry(data));
    }

    public ResponseData get(TimeSpan ts) {
        CacheEntry entry = this.dataStore.get(ts);
        if (entry == null || entry.getAge() + this.maxAge < (new Date()).getTime() / 1000) {
            this.misses++;
            return null;
        }
        this.hits++;
        return entry.getData();
    }

    //Typed-Get, will be obsolete after resolving issues #27 & #28
    public ResponseData get(Class<? extends ResponseData> type, TimeSpan ts){
        CacheEntry entry = this.dataStore.get(ts);
        if (entry == null || !type.isInstance(entry.getData()) || entry.getAge() + this.maxAge < (new Date()).getTime() / 1000) {
            this.misses++;
            return null;
        }
        this.hits++;
        return entry.getData();
    }

    //Functions for Statistics & Debugging

    public int getHits() {
        return this.hits;
    }

    public int getMisses() {
        return this.misses;
    }

}
