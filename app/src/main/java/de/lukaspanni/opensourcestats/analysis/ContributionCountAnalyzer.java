package de.lukaspanni.opensourcestats.analysis;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import de.lukaspanni.opensourcestats.data.ContributionCount;
import de.lukaspanni.opensourcestats.data.TimeSpan;
import de.lukaspanni.opensourcestats.data.UserContributionsResponse;
import de.lukaspanni.opensourcestats.repository.UserContributionsRepository;

public class ContributionCountAnalyzer {

    private ArrayList<ContributionCount> contributions;
    int fullCollectionSize = 0; //to check if all data is loaded
    private UserContributionsRepository repository;
    private DataLoadedCallback callback;

    public ContributionCountAnalyzer(UserContributionsRepository repository, TimeSpan timeSpan){
        this(repository, timeSpan, null);
    }

    public ContributionCountAnalyzer(UserContributionsRepository repository, TimeSpan timeSpan, DataLoadedCallback executeWhenReady) {
        this.repository = repository;
        contributions = new ArrayList<>();
        callback = executeWhenReady;
        loadData(timeSpan);
    }

    private void loadData(TimeSpan totalTimeSpan) {
        //For every day in totalTimeSpan load a ContributionCount Object
        Date dayDate = totalTimeSpan.getStart();
        TimeSpan dayTimeSpan;
        do {
            dayTimeSpan = new TimeSpan(dayDate, dayDate);
            repository.loadUserContributionsInTimeSpan(dayTimeSpan, response -> {
                contributions.add(((UserContributionsResponse) response).getContributionCount());
                executeCallbackWhenReady();
            }, false);
            fullCollectionSize++;
            //Move to next date
            dayDate = getOneDayAfter(dayDate);
        }while(!dayDate.equals(totalTimeSpan.getEnd()));
    }

    private Date getOneDayAfter(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

    private void executeCallbackWhenReady(){
        if(callback == null) return;
        if(isReady()) callback.callback();
    }

    public boolean isReady(){
        return contributions.size() == fullCollectionSize;
    }

    public List<ContributionCount> getTop1(ContributionCountRanking ranking) {
        return getTopK(ranking, 1);
    }

    public List<ContributionCount> getTopK(ContributionCountRanking ranking, int k) {
        if(fullCollectionSize < k) throw new IllegalArgumentException("Not enough data provided. Cannot get top "+k+" Elements.");
        if(!isReady()) throw new IllegalStateException("Not enough data loaded to execute this action");
        return ranking.rank(contributions).stream().limit(k).collect(Collectors.toList());
    }

    public interface DataLoadedCallback {
        void callback();
    }
}
