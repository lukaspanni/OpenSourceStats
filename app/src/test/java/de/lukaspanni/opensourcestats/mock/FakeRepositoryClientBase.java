package de.lukaspanni.opensourcestats.mock;

import de.lukaspanni.opensourcestats.client.ClientDataCallback;

public abstract class FakeRepositoryClientBase {

    private boolean called = false;
    private ClientDataCallback calledCallback;

    public ClientDataCallback getCalledCallback() {
        return calledCallback;
    }

    public boolean isCalled() {
        return called;
    }

    protected void setCalledCallback(ClientDataCallback callback){
        this.called = true;
        this.calledCallback = callback;
    }
}
