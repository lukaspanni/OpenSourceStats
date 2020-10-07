package de.lukaspanni.opensourcestats.ui.dashboard;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import de.lukaspanni.opensourcestats.auth.AuthHandler;
import de.lukaspanni.opensourcestats.client.ClientDataCallback;
import de.lukaspanni.opensourcestats.client.GHClient;
import de.lukaspanni.opensourcestats.client.ResponseData;
import de.lukaspanni.opensourcestats.client.UserContributionsResponse;



public class DashboardViewModel extends ViewModel {

    private MutableLiveData<Integer> cwCommitCount;
    private MutableLiveData<Integer> cwIssueCount;
    private MutableLiveData<Integer> cwPullRequestCount;
    private MutableLiveData<Integer> cwPullRequestReviewCount;
    private MutableLiveData<Integer> lwCommitCount;
    private MutableLiveData<Integer> lwIssueCount;
    private MutableLiveData<Integer> lwPullRequestCount;
    private MutableLiveData<Integer> lwPullRequestReviewCount;
    private GHClient client;


    public DashboardViewModel() {
        cwCommitCount = new MutableLiveData<>();
        cwIssueCount = new MutableLiveData<>();
        cwPullRequestCount = new MutableLiveData<>();
        cwPullRequestReviewCount = new MutableLiveData<>();
        lwCommitCount = new MutableLiveData<>();
        lwIssueCount = new MutableLiveData<>();
        lwPullRequestCount = new MutableLiveData<>();
        lwPullRequestReviewCount = new MutableLiveData<>();
    }

    public MutableLiveData<Integer> getCurrentWeekCommitCount() {
        return cwCommitCount;
    }

    public MutableLiveData<Integer> getCurrentWeekIssueCount() {
        return cwIssueCount;
    }

    public MutableLiveData<Integer> getCurrentWeekPullRequestCount() {
        return cwPullRequestCount;
    }

    public MutableLiveData<Integer> getCurrentWeekPullRequestReviewCount() {
        return cwPullRequestReviewCount;
    }

    public MutableLiveData<Integer> getLastWeekCommitCount() {
        return lwCommitCount;
    }

    public MutableLiveData<Integer> getLastWeekIssueCount() {
        return lwIssueCount;
    }

    public MutableLiveData<Integer> getLastWeekPullRequestCount() {
        return lwPullRequestCount;
    }

    public MutableLiveData<Integer> getLastWeekPullRequestReviewCount() {
        return lwPullRequestReviewCount;
    }

    //TODO: Extract common interface
    public void loadData(AuthHandler handler){
        if (handler.checkAuth()) {
            if (client == null) {
                client = new GHClient(handler);
            }
            client.userContributionsCurrentWeek(new ClientDataCallback() {
                @Override
                public void callback(ResponseData data) {
                    UserContributionsResponse responseData = (UserContributionsResponse) data;
                    if (data == null) return;
                    cwCommitCount.postValue(responseData.getCommits());
                    cwIssueCount.postValue(responseData.getIssues());
                    cwPullRequestCount.postValue(responseData.getPullRequests());
                    cwPullRequestReviewCount.postValue(responseData.getPullRequestReviews());
                }
            });
            client.userContributionsLastWeek(new ClientDataCallback() {
                @Override
                public void callback(ResponseData data) {
                    UserContributionsResponse responseData = (UserContributionsResponse) data;
                    if (data == null) return;
                    lwCommitCount.postValue(responseData.getCommits());
                    lwIssueCount.postValue(responseData.getIssues());
                    lwPullRequestCount.postValue(responseData.getPullRequests());
                    lwPullRequestReviewCount.postValue(responseData.getPullRequestReviews());
                }
            });
        }
    }


}