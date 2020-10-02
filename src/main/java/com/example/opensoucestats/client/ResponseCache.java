package com.example.opensoucestats.client;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ResponseCache {

    private Map<TimeSpan, CacheEntry> dataStore = new HashMap<>();
    private long maxAge; // in seconds
    private static long DEFAULT_MAXAGE = 3600;

    public ResponseCache() {
        this(DEFAULT_MAXAGE);
    }

    public ResponseCache(long maxAge) {
        this.maxAge = maxAge;
    }

    public void put(TimeSpan ts, ResponseData data) {
        this.dataStore.put(ts, new CacheEntry(data));
    }

    public ResponseData get(TimeSpan ts) {
        CacheEntry entry = this.dataStore.get(ts);
        if (entry == null || entry.getAge() + this.maxAge > (new Date()).getTime() / 1000) {
            return null;
        }
        return entry.getData();
    }

}
