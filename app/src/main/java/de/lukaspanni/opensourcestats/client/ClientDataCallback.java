package de.lukaspanni.opensourcestats.client;

import de.lukaspanni.opensourcestats.data.ResponseData;

public interface ClientDataCallback {
    void callback(ResponseData response);
}