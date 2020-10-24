package de.lukaspanni.opensourcestats.ui.dashboard;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;

import de.lukaspanni.opensourcestats.ui.DataAccessViewModel;
import de.lukaspanni.opensourcestats.auth.AuthHandler;
import de.lukaspanni.opensourcestats.client.ContributionCount;
import de.lukaspanni.opensourcestats.client.GHClient;
import de.lukaspanni.opensourcestats.data.UserContributionsResponse;


public class DashboardViewModel extends ViewModel implements DataAccessViewModel {
    private MutableLiveData<ContributionCount> currentWeekContributions;
    private MutableLiveData<ContributionCount> lastWeekContributions;
    private MutableLiveData<ContributionCount> currentMonthContributions;
    private MutableLiveData<ContributionCount> lastMonthContributions;

    private GHClient client;

    public DashboardViewModel() {
        currentWeekContributions = new MutableLiveData<>();
        lastWeekContributions = new MutableLiveData<>();
        currentMonthContributions = new MutableLiveData<>();
        lastMonthContributions = new MutableLiveData<>();
    }

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

    public void loadData(@NotNull AuthHandler handler, boolean forceReload) {
        if (handler.checkAuth()) {
            if (client == null) {
                client = new GHClient(handler);
            }
            client.userContributionsCurrentWeek(data -> dataCallback(currentWeekContributions, (UserContributionsResponse) data), forceReload);
            client.userContributionsLastWeek(data -> dataCallback(lastWeekContributions, (UserContributionsResponse) data), forceReload);

            client.userContributionsCurrentMonth(data -> dataCallback(currentMonthContributions, (UserContributionsResponse) data), forceReload);
            client.userContributionsLastMonth(data -> dataCallback(lastMonthContributions, (UserContributionsResponse) data), forceReload);
        }
    }

    private void dataCallback(MutableLiveData<ContributionCount> liveData, UserContributionsResponse responseData) {
        if (responseData == null) return;
        liveData.postValue(new ContributionCount(
                responseData.getCommits(),
                responseData.getIssues(),
                responseData.getPullRequests(),
                responseData.getPullRequestReviews()
        ));
    }


}