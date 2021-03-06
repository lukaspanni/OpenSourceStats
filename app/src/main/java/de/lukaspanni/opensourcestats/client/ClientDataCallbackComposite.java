package de.lukaspanni.opensourcestats.client;

import de.lukaspanni.opensourcestats.data.ResponseData;

public class ClientDataCallbackComposite implements ClientDataCallback {

    private ClientDataCallback originalCallback;
    private ClientDataCallback additionalCallback;

    public ClientDataCallbackComposite(ClientDataCallback originalCallback, ClientDataCallback additionalCallback) {
        this.originalCallback = originalCallback;
        this.additionalCallback = additionalCallback;
    }

    @Override
    public void callback(ResponseData response) {
        if (originalCallback != null) originalCallback.callback(response);
        if (additionalCallback != null) additionalCallback.callback(response);
    }
}
