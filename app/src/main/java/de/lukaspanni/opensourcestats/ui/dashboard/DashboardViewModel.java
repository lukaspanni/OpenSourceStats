package de.lukaspanni.opensourcestats.ui.dashboard;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import org.jetbrains.annotations.NotNull;

import de.lukaspanni.opensourcestats.OpenSourceStatsApplication;
import de.lukaspanni.opensourcestats.analysis.ContributionCountAnalyzer;
import de.lukaspanni.opensourcestats.analysis.ContributionCountCommitRanking;
import de.lukaspanni.opensourcestats.analysis.ContributionCountRanking;
import de.lukaspanni.opensourcestats.analysis.ContributionCountTotalContributionsRanking;
import de.lukaspanni.opensourcestats.data.TimeSpan;
import de.lukaspanni.opensourcestats.data.TimeSpanFactory;
import de.lukaspanni.opensourcestats.repository.UserContributionsRepository;
import de.lukaspanni.opensourcestats.ui.DataAccessViewModel;
import de.lukaspanni.opensourcestats.data.ContributionCount;
import de.lukaspanni.opensourcestats.data.UserContributionsResponse;


public class DashboardViewModel extends AndroidViewModel implements DataAccessViewModel {
    private MutableLiveData<ContributionCount> currentWeekContributions;
    private MutableLiveData<ContributionCount> lastWeekContributions;
    private MutableLiveData<ContributionCount> currentMonthContributions;
    private MutableLiveData<ContributionCount> lastMonthContributions;

    private MutableLiveData<ContributionCount> mostTotalContributions;
    private MutableLiveData<ContributionCount> mostCommits;
    private ContributionCountAnalyzer analyzer;
    private ContributionCountRanking commitRanking;
    private ContributionCountRanking totalContributionsRanking;


    public DashboardViewModel(Application app) {
        super(app);
        currentWeekContributions = new MutableLiveData<>();
        lastWeekContributions = new MutableLiveData<>();
        currentMonthContributions = new MutableLiveData<>();
        lastMonthContributions = new MutableLiveData<>();

        mostTotalContributions = new MutableLiveData<>();
        mostCommits = new MutableLiveData<>();
        commitRanking = new ContributionCountCommitRanking();
        totalContributionsRanking = new ContributionCountTotalContributionsRanking();
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

    public MutableLiveData<ContributionCount> getMostTotalContributions() {
        return mostTotalContributions;
    }

    public MutableLiveData<ContributionCount> getMostCommits() {
        return mostCommits;
    }

    public void loadData(boolean forceReload) {
        OpenSourceStatsApplication application = (OpenSourceStatsApplication) getApplication();
        UserContributionsRepository repository = application.getUserContributionsRepository();
        if(analyzer == null)
            analyzer = new ContributionCountAnalyzer(repository, TimeSpanFactory.getCurrentWeek());
        analyzer.setCallback(getAnalyzerCallback());

        repository.loadUserContributionsInCurrentWeek(data -> dataCallback(currentWeekContributions, (UserContributionsResponse) data), forceReload);
        repository.loadUserContributionsInLastWeek(data -> dataCallback(lastWeekContributions, (UserContributionsResponse) data), forceReload);
        repository.loadUserContributionsInCurrentMonth(data -> dataCallback(currentMonthContributions, (UserContributionsResponse) data), forceReload);
        repository.loadUserContributionsInLastMonth(data -> dataCallback(lastMonthContributions, (UserContributionsResponse) data), forceReload);

    }

    @NotNull
    private ContributionCountAnalyzer.DataLoadedCallback getAnalyzerCallback() {
        return () -> {
            if(analyzer != null) {
                mostTotalContributions.postValue(analyzer.getTop1(totalContributionsRanking));
                mostCommits.postValue(analyzer.getTop1(commitRanking));
            }
        };
    }

    private void dataCallback(MutableLiveData<ContributionCount> liveData, UserContributionsResponse responseData) {
        if (responseData == null) return;
        liveData.postValue(responseData.getContributionCount());
    }


}