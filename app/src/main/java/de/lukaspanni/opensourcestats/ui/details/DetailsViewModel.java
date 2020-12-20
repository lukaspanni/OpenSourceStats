package de.lukaspanni.opensourcestats.ui.details;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import de.lukaspanni.opensourcestats.OpenSourceStatsApplication;
import de.lukaspanni.opensourcestats.repository.UserContributionsDataStore;
import de.lukaspanni.opensourcestats.data.ContributionRepositories;
import de.lukaspanni.opensourcestats.data.UserContributionsResponse;
import de.lukaspanni.opensourcestats.util.TimeSpan;


public class DetailsViewModel extends AndroidViewModel {

    private MutableLiveData<List<String>> commitRepositories;
    private MutableLiveData<List<String>> issueRepositories;
    private MutableLiveData<List<String>> pullRequestRepositories;
    private MutableLiveData<List<String>> pullRequestReviewRepositories;

    public DetailsViewModel(Application app) {
        super(app);
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


    public void loadData(TimeSpan timeSpan) {
        OpenSourceStatsApplication application = (OpenSourceStatsApplication) getApplication();
        UserContributionsDataStore repository = application.getUserContributionsRepository();

        repository.userContributionsTimeSpan(timeSpan, data -> {
            UserContributionsResponse responseData = (UserContributionsResponse) data;
            if (data == null) return;
            ContributionRepositories repositories = responseData.getContributionRepositories();
            //TODO: Update internal fields to sets
            commitRepositories.postValue(new ArrayList<>(repositories.getCommitRepositories()));
            issueRepositories.postValue(new ArrayList<>(repositories.getIssueRepositories()));
            pullRequestRepositories.postValue(new ArrayList<>(repositories.getPullRequestRepositories()));
            pullRequestReviewRepositories.postValue(new ArrayList<>(repositories.getPullRequestReviewRepositories()));
        }, false);

    }

}