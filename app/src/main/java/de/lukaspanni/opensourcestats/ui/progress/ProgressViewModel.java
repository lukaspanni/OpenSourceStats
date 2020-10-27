package de.lukaspanni.opensourcestats.ui.progress;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;

import de.lukaspanni.opensourcestats.client.UserContributionsClient;
import de.lukaspanni.opensourcestats.ui.DataAccessViewModel;
import de.lukaspanni.opensourcestats.auth.AuthHandler;
import de.lukaspanni.opensourcestats.client.ContributionCount;
import de.lukaspanni.opensourcestats.client.GHClient;
import de.lukaspanni.opensourcestats.data.UserContributionsResponse;

public class ProgressViewModel extends ViewModel implements DataAccessViewModel {

    private MutableLiveData<ContributionCount> currentWeekContributions;
    private MutableLiveData<ContributionCount> lastWeekContributions;
    private MutableLiveData<ContributionCount> currentMonthContributions;
    private MutableLiveData<ContributionCount> lastMonthContributions;
    private UserContributionsClient client;


    public MutableLiveData<ContributionCount> getCurrentWeekContributions() {
        return currentWeekContributions;
    }

    public MutableLiveData<ContributionCount> getLastWeekContributions() {
        return lastWeekContributions;
    }

    public MutableLiveData<ContributionCount> getCurrentMonthContributions() {
        return currentMonthContributions;
    }

    public MutableLiveData<ContributionCount> getLastMonthContributions() {
        return lastMonthContributions;
    }

    public ProgressViewModel() {
        currentWeekContributions = new MutableLiveData<>();
        lastWeekContributions = new MutableLiveData<>();
        currentMonthContributions = new MutableLiveData<>();
        lastMonthContributions = new MutableLiveData<>();
    }

    public void loadData(@NotNull AuthHandler handler, boolean forceReload) {
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

            client.userContributionsCurrentMonth(data -> {
                UserContributionsResponse currentMonthData = (UserContributionsResponse) data;
                if (data == null) return;
                currentMonthContributions.postValue(new ContributionCount(
                        currentMonthData.getCommits(),
                        currentMonthData.getIssues(),
                        currentMonthData.getPullRequests(),
                        currentMonthData.getPullRequestReviews()
                ));
            }, forceReload);
            client.userContributionsLastMonth(data -> {
                UserContributionsResponse lastMonthData = (UserContributionsResponse) data;
                if (data == null) return;
                lastMonthContributions.postValue(new ContributionCount(
                        lastMonthData.getCommits(),
                        lastMonthData.getIssues(),
                        lastMonthData.getPullRequests(),
                        lastMonthData.getPullRequestReviews()
                ));
            }, forceReload);
        }
    }


}