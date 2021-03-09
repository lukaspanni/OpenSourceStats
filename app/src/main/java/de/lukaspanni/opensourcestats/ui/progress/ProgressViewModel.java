package de.lukaspanni.opensourcestats.ui.progress;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import de.lukaspanni.opensourcestats.OpenSourceStatsApplication;
import de.lukaspanni.opensourcestats.client.ClientDataCallback;
import de.lukaspanni.opensourcestats.repository.UserContributionsRepository;
import de.lukaspanni.opensourcestats.ui.DataAccessViewModel;
import de.lukaspanni.opensourcestats.data.ContributionCount;
import de.lukaspanni.opensourcestats.data.UserContributionsResponse;
import org.jetbrains.annotations.NotNull;

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
        repository.loadUserContributionsInCurrentWeek(getClientDataCallback(currentWeekContributions), forceReload);
        repository.loadUserContributionsInLastWeek(getClientDataCallback(lastWeekContributions), forceReload);

        repository.loadUserContributionsInCurrentMonth(getClientDataCallback(currentMonthContributions), forceReload);
        repository.loadUserContributionsInLastMonth(getClientDataCallback(lastMonthContributions), forceReload);

    }

    @NotNull
    private ClientDataCallback getClientDataCallback(MutableLiveData<ContributionCount> currentWeekContributions) {
        return data -> {
            UserContributionsResponse currentWeekData = (UserContributionsResponse) data;
            if (data == null) return;
            currentWeekContributions.postValue(currentWeekData.getContributionCount());
        };
    }


}