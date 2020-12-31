package de.lukaspanni.opensourcestats;

import android.app.Application;

import de.lukaspanni.opensourcestats.auth.AuthHandler;
import de.lukaspanni.opensourcestats.auth.AuthHandlerActivity;
import de.lukaspanni.opensourcestats.repository.RepositoryDataRepository;
import de.lukaspanni.opensourcestats.repository.UserContributionsRepository;

public class OpenSourceStatsApplication extends Application {

    private AuthHandler authHandler;
    private UserContributionsRepository userContributionsRepository;
    private RepositoryDataRepository repositoryDataRepository;

    public UserContributionsRepository getUserContributionsRepository() {
        return userContributionsRepository;
    }

    public void setUserContributionsRepository(UserContributionsRepository userContributionsRepository) {
        if(userContributionsRepository != null)
            this.userContributionsRepository = userContributionsRepository;
    }

    public RepositoryDataRepository getRepositoryDataRepository() {
        return repositoryDataRepository;
    }

    public void setRepositoryDataRepository(RepositoryDataRepository repositoryDataRepository) {
        if(repositoryDataRepository != null)
            this.repositoryDataRepository = repositoryDataRepository;
    }

    public AuthHandler getAuthHandler(AuthHandlerActivity authHandlerActivity) {
        if(authHandler != null){
            authHandler.setAuthHandlerActivity(authHandlerActivity);
            return authHandler;
        }
        authHandler = new AuthHandler(authHandlerActivity);
        return authHandler;
    }

}
