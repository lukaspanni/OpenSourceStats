package de.lukaspanni.opensourcestats;

import android.app.Application;

import de.lukaspanni.opensourcestats.repository.RepositoryDataRepository;
import de.lukaspanni.opensourcestats.repository.UserContributionsRepository;

public class OpenSourceStatsApplication extends Application {
    private UserContributionsRepository userContributionsRepository;
    private RepositoryDataRepository repositoryDataRepository;

    public UserContributionsRepository getUserContributionsRepository() {
        return userContributionsRepository;
    }

    public void setUserContributionsRepository(UserContributionsRepository userContributionsRepository) {
        this.userContributionsRepository = userContributionsRepository;
    }

    public RepositoryDataRepository getRepositoryDataRepository() {
        return repositoryDataRepository;
    }

    public void setRepositoryDataRepository(RepositoryDataRepository repositoryDataRepository) {
        this.repositoryDataRepository = repositoryDataRepository;
    }
}
