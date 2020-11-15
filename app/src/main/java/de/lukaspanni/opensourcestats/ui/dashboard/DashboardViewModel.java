package de.lukaspanni.opensourcestats.ui.dashboard;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;

import de.lukaspanni.opensourcestats.OpenSourceStatsApplication;
import de.lukaspanni.opensourcestats.repository.UserContributionsDataStore;
import de.lukaspanni.opensourcestats.ui.DataAccessViewModel;
import de.lukaspanni.opensourcestats.auth.AuthHandler;
import de.lukaspanni.opensourcestats.data.ContributionCount;
import de.lukaspanni.opensourcestats.client.GHClient;
import de.lukaspanni.opensourcestats.data.UserContributionsResponse;


public class DashboardViewModel extends AndroidViewModel implements DataAccessViewModel {
    private MutableLiveData<ContributionCount> currentWeekContributions;
    private MutableLiveData<ContributionCount> lastWeekContributions;
    private MutableLiveData<ContributionCount> currentMonthContributions;
    private MutableLiveData<ContributionCount> lastMonthContributions;


    public DashboardViewModel(Application app) {
        super(app);
        currentWeekContributions = new MutableLiveData<>();
        lastWeekContributions = new MutableLiveData<>();
        currentMonthContributions = new MutableLiveData<>();
        lastMonthContributions = new MutableLiveData<>();
    }

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

    public void loadData(boolean forceReload) {
        OpenSourceStatsApplication application = (OpenSourceStatsApplication) getApplication();
        UserContributionsDataStore repository = application.getUserContributionsRepository();
        repository.userContributionsCurrentWeek(data -> dataCallback(currentWeekContributions, (UserContributionsResponse) data), forceReload);
        repository.userContributionsLastWeek(data -> dataCallback(lastWeekContributions, (UserContributionsResponse) data), forceReload);
        repository.userContributionsCurrentMonth(data -> dataCallback(currentMonthContributions, (UserContributionsResponse) data), forceReload);
        repository.userContributionsLastMonth(data -> dataCallback(lastMonthContributions, (UserContributionsResponse) data), forceReload);

    }

    private void dataCallback(MutableLiveData<ContributionCount> liveData, UserContributionsResponse responseData) {
        if (responseData == null) return;
        liveData.postValue(responseData.getContributionCount());
    }


}