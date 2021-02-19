package de.lukaspanni.opensourcestats;

import android.app.Application;

import de.lukaspanni.opensourcestats.auth.GithubOAuthHandler;
import de.lukaspanni.opensourcestats.auth.AuthHandlerActivity;
import de.lukaspanni.opensourcestats.repository.RepositoryDataRepository;
import de.lukaspanni.opensourcestats.repository.UserContributionsRepository;

public class OpenSourceStatsApplication extends Application {

    private GithubOAuthHandler authHandler;
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

    public GithubOAuthHandler getAuthHandler(AuthHandlerActivity authHandlerActivity) {
        if(authHandler != null){
            authHandler.setAuthHandlerActivity(authHandlerActivity);
            return authHandler;
        }
        authHandler = new GithubOAuthHandler(authHandlerActivity);
        return authHandler;
    }

    public void clearRepositoryCaches(){
        userContributionsRepository.clearCache();
        repositoryDataRepository.clearCache();
    }

}
