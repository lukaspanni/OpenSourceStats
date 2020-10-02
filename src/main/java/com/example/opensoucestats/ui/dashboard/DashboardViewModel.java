package com.example.opensoucestats.ui.dashboard;

import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.opensoucestats.auth.AuthHandler;
import com.example.opensoucestats.client.ClientDataCallback;
import com.example.opensoucestats.client.GHClient;
import com.example.opensoucestats.client.ResponseData;
import com.example.opensoucestats.client.UserContributionsResponse;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<String> overviewText;
    private MutableLiveData<List<String>> repositories;
    private GHClient client;
    private AuthHandler handler;

    public DashboardViewModel() {
        overviewText = new MutableLiveData<>();
        repositories = new MutableLiveData<>();
    }

    public LiveData<String> getOverview() {
        return overviewText;
    }

    public LiveData<List<String>> getRepositories() {
        return repositories;
    }

    public void loadData(Activity activity) {
        if (handler == null) {
            handler = AuthHandler.getInstance(activity);
        }
        if (handler.checkAuth()) {
            if (client == null) {
                client = new GHClient(handler);
            }
            Date start = new Date(System.currentTimeMillis() - (7 * 1000 * 60 * 60 * 24));
            Date end = new Date();
            client.loadData(start, end, new ClientDataCallback() {
                @Override
                public void callback(ResponseData data) {
                    UserContributionsResponse responseData = (UserContributionsResponse) data;
                    if (data == null) return;
                    StringBuilder overviewOut = new StringBuilder("Current Week:\n");
                    overviewOut.append("Commits: ").append(responseData.getCommits()).append("\n");
                    overviewOut.append("Issues: ").append(responseData.getIssues()).append("\n");
                    overviewOut.append("PullRequests: ").append(responseData.getPullRequests()).append("\n");
                    overviewText.postValue(overviewOut.toString());
                    repositories.postValue(Stream.concat(Stream.concat(responseData.getCommitRepositories().stream(), responseData.getIssueRepositories().stream()), responseData.getPullRequestRepositories().stream()).collect(Collectors.toList()));
                }
            });
        }
    }
}