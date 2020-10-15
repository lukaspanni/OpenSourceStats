package de.lukaspanni.opensourcestats.ui.progress;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import de.lukaspanni.opensourcestats.auth.AuthHandler;
import de.lukaspanni.opensourcestats.client.ContributionCount;
import de.lukaspanni.opensourcestats.client.GHClient;
import de.lukaspanni.opensourcestats.client.UserContributionsResponse;

public class ProgressViewModel extends ViewModel {

    private MutableLiveData<ContributionCount> currentWeekContributions;
    private MutableLiveData<ContributionCount> lastWeekContributions;
    private GHClient client;


    public MutableLiveData<ContributionCount> getCurrentWeekContributions() {
        return currentWeekContributions;
    }

    public MutableLiveData<ContributionCount> getLastWeekContributions() {
        return lastWeekContributions;
    }

    public ProgressViewModel() {
        currentWeekContributions = new MutableLiveData<>();
        lastWeekContributions = new MutableLiveData<>();
    }

    public void loadData(AuthHandler handler, boolean forceReload) {
        if (handler.checkAuth()) {
            if (client == null) {
                client = new GHClient(handler);
            }
            client.userContributionsCurrentWeek(data -> {
                UserContributionsResponse currentWeekData = (UserContributionsResponse) data;
                if (data == null) return;
                currentWeekContributions.postValue(new ContributionCount(
                        currentWeekData.getCommits(),
                        currentWeekData.getIssues(),
                        currentWeekData.getPullRequests(),
                        currentWeekData.getPullRequestReviews()
                ));
            }, forceReload);
            client.userContributionsLastWeek(data -> {
                UserContributionsResponse lastWeekData = (UserContributionsResponse) data;
                if (data == null) return;
                lastWeekContributions.postValue(new ContributionCount(
                        lastWeekData.getCommits(),
                        lastWeekData.getIssues(),
                        lastWeekData.getPullRequests(),
                        lastWeekData.getPullRequestReviews()
                ));
            }, forceReload);
        }
    }


}