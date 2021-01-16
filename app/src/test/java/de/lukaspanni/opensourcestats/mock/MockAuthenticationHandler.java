package de.lukaspanni.opensourcestats.mock;

import de.lukaspanni.opensourcestats.auth.AuthenticatedAction;
import de.lukaspanni.opensourcestats.auth.AuthenticationHandler;

public class MockAuthenticationHandler implements AuthenticationHandler {

    private AuthenticatedAction lastExecutedAction;

    public AuthenticatedAction getLastExecutedAction() {
        return lastExecutedAction;
    }

    @Override
    public boolean checkAuth() {
        return true;
    }

    @Override
    public void performActionWithToken(AuthenticatedAction action) {
        this.lastExecutedAction = action;
        action.execute("NONE", "NONE", null);
    }

    @Override
    public void authenticate() { }
}
