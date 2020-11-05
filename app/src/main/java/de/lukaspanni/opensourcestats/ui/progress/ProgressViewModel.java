package de.lukaspanni.opensourcestats.ui.progress;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;

import de.lukaspanni.opensourcestats.client.UserContributionsClient;
import de.lukaspanni.opensourcestats.ui.DataAccessViewModel;
import de.lukaspanni.opensourcestats.auth.AuthHandler;
import de.lukaspanni.opensourcestats.data.ContributionCount;
import de.lukaspanni.opensourcestats.client.GHClient;
import de.lukaspanni.opensourcestats.data.UserContributionsResponse;

public class ProgressViewModel extends ViewModel implements DataAccessViewModel {

    private MutableLiveData<ContributionCount> currentWeekContributions;
    private MutableLiveData<ContributionCount> lastWeekContributions;
    private MutableLiveData<ContributionCount> currentMonthContributions;
    private MutableLiveData<ContributionCount> lastMonthContributions;
    private UserContributionsClient client;


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

    public ProgressViewModel() {
        currentWeekContributions = new MutableLiveData<>();
        lastWeekContributions = new MutableLiveData<>();
        currentMonthContributions = new MutableLiveData<>();
        lastMonthContributions = new MutableLiveData<>();
    }

    public void loadData(@NotNull AuthHandler handler, boolean forceReload) {
        if (handler.checkAuth()) {
            if (client == null) {
                client = new GHClient(handler);
            }
            client.userContributionsCurrentWeek(data -> {
                UserContributionsResponse currentWeekData = (UserContributionsResponse) data;
                if (data == null) return;
                currentWeekContributions.postValue(currentWeekData.getContributionCount());
            }, forceReload);
            client.userContributionsLastWeek(data -> {
                UserContributionsResponse lastWeekData = (UserContributionsResponse) data;
                if (data == null) return;
                lastWeekContributions.postValue(lastWeekData.getContributionCount());
            }, forceReload);

            client.userContributionsCurrentMonth(data -> {
                UserContributionsResponse currentMonthData = (UserContributionsResponse) data;
                if (data == null) return;
                currentMonthContributions.postValue(currentMonthData.getContributionCount());
            }, forceReload);
            client.userContributionsLastMonth(data -> {
                UserContributionsResponse lastMonthData = (UserContributionsResponse) data;
                if (data == null) return;
                lastMonthContributions.postValue(lastMonthData.getContributionCount());
            }, forceReload);
        }
    }


}