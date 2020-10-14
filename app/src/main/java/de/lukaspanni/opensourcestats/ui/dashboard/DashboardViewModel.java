package de.lukaspanni.opensourcestats.ui.dashboard;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import de.lukaspanni.opensourcestats.auth.AuthHandler;
import de.lukaspanni.opensourcestats.client.ClientDataCallback;
import de.lukaspanni.opensourcestats.client.ContributionCount;
import de.lukaspanni.opensourcestats.client.GHClient;
import de.lukaspanni.opensourcestats.client.ResponseData;
import de.lukaspanni.opensourcestats.client.UserContributionsResponse;


public class DashboardViewModel extends ViewModel {
    private MutableLiveData<ContributionCount> currentWeekContributions;
    private MutableLiveData<ContributionCount> lastWeekContributions;

    private GHClient client;

    public DashboardViewModel() {
        currentWeekContributions = new MutableLiveData<>();
        lastWeekContributions = new MutableLiveData<>();
    }

    public MutableLiveData<ContributionCount> getCurrentWeekContributions(){
        return currentWeekContributions;
    }

    public MutableLiveData<ContributionCount> getLastWeekContributions() {
        return lastWeekContributions;
    }

    //TODO: Extract common interface
    public void loadData(AuthHandler handler) {
        if (handler.checkAuth()) {
            if (client == null) {
                client = new GHClient(handler);
            }
            client.userContributionsCurrentWeek(new ClientDataCallback() {
                @Override
                public void callback(ResponseData data) {
                    UserContributionsResponse responseData = (UserContributionsResponse) data;
                    if (data == null) return;
                    currentWeekContributions.postValue(new ContributionCount(
                            responseData.getCommits(),
                            responseData.getIssues(),
                            responseData.getPullRequests(),
                            responseData.getPullRequestReviews()
                    ));
                }
            });
            client.userContributionsLastWeek(new ClientDataCallback() {
                @Override
                public void callback(ResponseData data) {
                    UserContributionsResponse responseData = (UserContributionsResponse) data;
                    if (data == null) return;
                    lastWeekContributions.postValue(new ContributionCount(
                            responseData.getCommits(),
                            responseData.getIssues(),
                            responseData.getPullRequests(),
                            responseData.getPullRequestReviews()
                    ));
                }
            });
        }
    }


}