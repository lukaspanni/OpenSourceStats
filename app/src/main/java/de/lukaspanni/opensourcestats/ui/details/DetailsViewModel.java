package de.lukaspanni.opensourcestats.ui.details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import de.lukaspanni.opensourcestats.auth.AuthHandler;
import de.lukaspanni.opensourcestats.client.Client;
import de.lukaspanni.opensourcestats.client.ClientDataCallback;
import de.lukaspanni.opensourcestats.client.GHClient;
import de.lukaspanni.opensourcestats.client.ResponseData;
import de.lukaspanni.opensourcestats.client.UserContributionsResponse;
import de.lukaspanni.opensourcestats.util.TimeSpan;


public class DetailsViewModel extends ViewModel {

    private MutableLiveData<List<String>> commitRepositories;
    private MutableLiveData<List<String>> issueRepositories;
    private MutableLiveData<List<String>> pullRequestRepositories;
    private MutableLiveData<List<String>> pullRequestReviewRepositories;
    private Client client;

    public DetailsViewModel() {
        commitRepositories = new MutableLiveData<>();
        issueRepositories = new MutableLiveData<>();
        pullRequestRepositories = new MutableLiveData<>();
        pullRequestReviewRepositories = new MutableLiveData<>();
    }

    public LiveData<List<String>> getCommitRepositories() {
        return commitRepositories;
    }

    public LiveData<List<String>> getIssueRepositories() {
        return issueRepositories;
    }

    public MutableLiveData<List<String>> getPullRequestRepositories() {
        return pullRequestRepositories;
    }

    public MutableLiveData<List<String>> getPullRequestReviewRepositories() {
        return pullRequestReviewRepositories;
    }


    public void loadData(TimeSpan week, AuthHandler handler) {
        if (handler.checkAuth()) {
            if (client == null) {
                client = new GHClient(handler);
            }
            //TODO: public client-Method to get based on timespan?
            client.userContributionsWeek(week.getStart(), new ClientDataCallback() {
                @Override
                public void callback(ResponseData data) {
                    UserContributionsResponse responseData = (UserContributionsResponse) data;
                    if (data == null) return;
                    commitRepositories.postValue(responseData.getCommitRepositories());
                    issueRepositories.postValue(responseData.getIssueRepositories());
                    pullRequestRepositories.postValue(responseData.getPullRequestRepositories());
                    pullRequestReviewRepositories.postValue(responseData.getPullRequestReviewRepositories());
                }
            }, false);
        }
    }

}