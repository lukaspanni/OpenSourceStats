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

    private MutableLiveData<String> overviewText;
    private GHClient client;
    private AuthHandler handler;


    public DashboardViewModel() {
        overviewText = new MutableLiveData<>();
    }

    public LiveData<String> getOverview() {
        return overviewText;
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
                    StringBuilder overviewOut = new StringBuilder("Current Week:\n");
                    overviewOut.append("Commits: ").append(responseData.getCommits()).append("\n");
                    overviewOut.append("Issues: ").append(responseData.getIssues()).append("\n");
                    overviewOut.append("PullRequests: ").append(responseData.getPullRequests()).append("\n");
                    overviewText.postValue(overviewOut.toString());
                }
            });
        }
    }


}