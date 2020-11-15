package de.lukaspanni.opensourcestats;

import android.app.Application;

import de.lukaspanni.opensourcestats.repository.UserContributionsRepository;

public class OpenSourceStatsApplication extends Application {
    private UserContributionsRepository userContributionsRepository;

    public UserContributionsRepository getUserContributionsRepository() {
        return userContributionsRepository;
    }

    public void setUserContributionsRepository(UserContributionsRepository userContributionsRepository) {
        this.userContributionsRepository = userContributionsRepository;
    }
}
