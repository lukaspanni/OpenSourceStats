package de.lukaspanni.opensourcestats.ui.dashboard;


import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import de.lukaspanni.opensourcestats.auth.AuthHandler;
import de.lukaspanni.opensourcestats.client.ClientDataCallback;
import de.lukaspanni.opensourcestats.client.GHClient;
import de.lukaspanni.opensourcestats.client.ResponseData;
import de.lukaspanni.opensourcestats.client.UserContributionsResponse;



public class DashboardViewModel extends ViewModel {

    private MutableLiveData<Integer> commitCount;
    private MutableLiveData<Integer> issueCount;
    private MutableLiveData<Integer> pullRequestCount;
    private MutableLiveData<Integer> pullRequestReviewCount;
    private GHClient client;
    private AuthHandler handler;


    public DashboardViewModel() {
        commitCount = new MutableLiveData<>();
        issueCount= new MutableLiveData<>();
        pullRequestCount = new MutableLiveData<>();
        pullRequestReviewCount = new MutableLiveData<>();
    }

    public MutableLiveData<Integer> getCommitCount() {
        return commitCount;
    }

    public MutableLiveData<Integer> getIssueCount() {
        return issueCount;
    }

    public MutableLiveData<Integer> getPullRequestCount() {
        return pullRequestCount;
    }

    public MutableLiveData<Integer> getPullRequestReviewCount() {
        return pullRequestReviewCount;
    }

    public void loadData(Activity activity){
        if (handler == null) {
            handler = AuthHandler.getInstance(activity);
        }
        if (handler.checkAuth()) {
            if (client == null) {
                client = new GHClient(handler);
            }
            client.userContributionsCurrentWeek(new ClientDataCallback() {
                @Override
                public void callback(ResponseData data) {
                    UserContributionsResponse responseData = (UserContributionsResponse) data;
                    if (data == null) return;
                    commitCount.postValue(responseData.getCommits());
                    issueCount.postValue(responseData.getIssues());
                    pullRequestCount.postValue(responseData.getPullRequests());
                    pullRequestReviewCount.postValue(responseData.getPullRequestReviews());
                }
            });
        }
    }


}