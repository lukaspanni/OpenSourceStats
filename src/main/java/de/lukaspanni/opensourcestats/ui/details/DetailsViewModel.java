package de.lukaspanni.opensourcestats.ui.details;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import de.lukaspanni.opensourcestats.auth.AuthHandler;
import de.lukaspanni.opensourcestats.client.ClientDataCallback;
import de.lukaspanni.opensourcestats.client.GHClient;
import de.lukaspanni.opensourcestats.client.ResponseData;
import de.lukaspanni.opensourcestats.client.UserContributionsResponse;

import java.util.List;


public class DetailsViewModel extends ViewModel {

    private MutableLiveData<List<String>> repositories;
    private GHClient client;
    private AuthHandler handler;

    public DetailsViewModel() {
        repositories = new MutableLiveData<>();
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
            client.userContributionsCurrentWeek(new ClientDataCallback() {
                @Override
                public void callback(ResponseData data) {
                    UserContributionsResponse responseData = (UserContributionsResponse) data;
                    if (data == null) return;
                    repositories.postValue(responseData.getAllContributionsRepositories());
                }
            });
        }
    }
}