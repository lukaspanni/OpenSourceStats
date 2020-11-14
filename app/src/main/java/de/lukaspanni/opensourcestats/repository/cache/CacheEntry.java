package de.lukaspanni.opensourcestats.repository.cache;

import java.util.Date;

import de.lukaspanni.opensourcestats.data.ResponseData;

/**
 * CacheEntry for @see {@link ResponseCache}
 */
public final class CacheEntry<T extends ResponseData> {

    private final T data;
    private final Date age;

    public T getData() {
        return data;
    }

    public long getAge() {
        return age.getTime() / 1000;
    }

    public CacheEntry(T data) {
        this(data, new Date());
    }

    private CacheEntry(T data, Date age) {
        this.data = data;
        this.age = age;
    }

}
