package de.lukaspanni.opensourcestats.ui.progress;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import de.lukaspanni.opensourcestats.OpenSourceStatsApplication;
import de.lukaspanni.opensourcestats.repository.UserContributionsRepository;
import de.lukaspanni.opensourcestats.ui.DataAccessViewModel;
import de.lukaspanni.opensourcestats.data.ContributionCount;
import de.lukaspanni.opensourcestats.data.UserContributionsResponse;

public class ProgressViewModel extends AndroidViewModel implements DataAccessViewModel {

    private MutableLiveData<ContributionCount> currentWeekContributions;
    private MutableLiveData<ContributionCount> lastWeekContributions;
    private MutableLiveData<ContributionCount> currentMonthContributions;
    private MutableLiveData<ContributionCount> lastMonthContributions;


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

    public ProgressViewModel(Application app) {
        super(app);
        currentWeekContributions = new MutableLiveData<>();
        lastWeekContributions = new MutableLiveData<>();
        currentMonthContributions = new MutableLiveData<>();
        lastMonthContributions = new MutableLiveData<>();
    }

    public void loadData(boolean forceReload) {
        OpenSourceStatsApplication application = (OpenSourceStatsApplication) getApplication();
        UserContributionsRepository repository = application.getUserContributionsRepository();
        repository.userContributionsCurrentWeek(data -> {
            UserContributionsResponse currentWeekData = (UserContributionsResponse) data;
            if (data == null) return;
            currentWeekContributions.postValue(currentWeekData.getContributionCount());
        }, forceReload);
        repository.userContributionsLastWeek(data -> {
            UserContributionsResponse lastWeekData = (UserContributionsResponse) data;
            if (data == null) return;
            lastWeekContributions.postValue(lastWeekData.getContributionCount());
        }, forceReload);

        repository.userContributionsCurrentMonth(data -> {
            UserContributionsResponse currentMonthData = (UserContributionsResponse) data;
            if (data == null) return;
            currentMonthContributions.postValue(currentMonthData.getContributionCount());
        }, forceReload);
        repository.userContributionsLastMonth(data -> {
            UserContributionsResponse lastMonthData = (UserContributionsResponse) data;
            if (data == null) return;
            lastMonthContributions.postValue(lastMonthData.getContributionCount());
        }, forceReload);

    }


}