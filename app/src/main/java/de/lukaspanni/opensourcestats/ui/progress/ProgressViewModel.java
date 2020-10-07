package de.lukaspanni.opensourcestats.ui.progress;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import de.lukaspanni.opensourcestats.auth.AuthHandler;
import de.lukaspanni.opensourcestats.client.ClientDataCallback;
import de.lukaspanni.opensourcestats.client.GHClient;
import de.lukaspanni.opensourcestats.client.ResponseData;
import de.lukaspanni.opensourcestats.client.UserContributionsResponse;

public class ProgressViewModel extends ViewModel {

    // TODO percent-type?
    private MutableLiveData<Float> weeklyCommitGain;
    private MutableLiveData<Float> weeklyIssueGain;
    private MutableLiveData<Float> weeklyPullRequestGain;
    private MutableLiveData<Float> weeklyPullRequestReviewGain;
    private GHClient client;


    public MutableLiveData<Float> getWeeklyCommitGain() {
        return weeklyCommitGain;
    }

    public MutableLiveData<Float> getWeeklyIssueGain() {
        return weeklyIssueGain;
    }

    public MutableLiveData<Float> getWeeklyPullRequestGain() {
        return weeklyPullRequestGain;
    }

    public MutableLiveData<Float> getWeeklyPullRequestReviewGain() {
        return weeklyPullRequestReviewGain;
    }

    public ProgressViewModel() {
        weeklyCommitGain = new MutableLiveData<>();
        weeklyIssueGain = new MutableLiveData<>();
        weeklyPullRequestGain = new MutableLiveData<>();
        weeklyPullRequestReviewGain = new MutableLiveData<>();
    }

    public void loadData(AuthHandler handler) {
        if (handler.checkAuth()) {
            if (client == null) {
                client = new GHClient(handler);
            }
            client.userContributionsCurrentWeek(new ClientDataCallback() {
                @Override
                public void callback(ResponseData data) {
                    UserContributionsResponse currentWeekData = (UserContributionsResponse) data;
                    if (data == null) return;
                    client.userContributionsLastWeek(new ClientDataCallback() {
                        @Override
                        public void callback(ResponseData data) {
                            UserContributionsResponse lastWeekData = (UserContributionsResponse) data;
                            if (data == null) return;
                            weeklyCommitGain.postValue(calculateGain(currentWeekData.getCommits(), lastWeekData.getCommits()));
                            weeklyIssueGain.postValue(calculateGain(currentWeekData.getIssues(), lastWeekData.getIssues()));
                            weeklyPullRequestGain.postValue(calculateGain(currentWeekData.getPullRequests(), lastWeekData.getPullRequests()));
                            weeklyPullRequestReviewGain.postValue(calculateGain(currentWeekData.getPullRequestReviews(), lastWeekData.getPullRequestReviews()));
                        }
                    });
                }
            });
        }
    }

    private float calculateGain(float current, float old) {
        if (old == 0) return 0;
        return (current / (float) old) - 1;
    }
}