package de.lukaspanni.opensourcestats.client.cache;

import java.util.Date;

import de.lukaspanni.opensourcestats.data.ResponseData;

/**
 * CacheEntry for @see {@link ResponseCache}
 */
public final class CacheEntry {

    private final ResponseData data;
    private final Date age;

    public ResponseData getData() {
        return data;
    }

    public long getAge() {
        return age.getTime() / 1000;
    }

    public CacheEntry(ResponseData data) {
        this(data, new Date());
    }

    public CacheEntry(ResponseData data, Date age) {
        this.data = data;
        this.age = age;
    }

}
