package de.lukaspanni.opensourcestats.auth;

public interface AuthenticationHandler {
    boolean checkAuth();

    void performActionWithToken(AuthenticatedAction action);

    void authenticate();
}
