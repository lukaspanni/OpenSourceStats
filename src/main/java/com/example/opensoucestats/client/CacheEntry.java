package com.example.opensoucestats.client;

import java.util.Date;

public class CacheEntry {

    private ResponseData data;
    private Date age;

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
