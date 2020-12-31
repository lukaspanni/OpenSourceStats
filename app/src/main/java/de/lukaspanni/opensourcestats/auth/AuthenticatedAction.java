package de.lukaspanni.opensourcestats.auth;

public interface AuthenticatedAction {
     void execute(String token, String idToken, Exception exception);
}
