package de.lukaspanni.opensourcestats.mock;

import de.lukaspanni.opensourcestats.client.ClientDataCallback;
import de.lukaspanni.opensourcestats.data.ResponseData;

public class MockClientCallback implements ClientDataCallback {

    private boolean callbackCalled = false;
    private ResponseData callResponseData = null;

    public boolean isCallbackCalled() {
        return callbackCalled;
    }

    public ResponseData getCallResponseData() {
        return callResponseData;
    }

    @Override
    public void callback(ResponseData response) {
        callbackCalled = true;
        callResponseData = response;
    }
}
