package de.lukaspanni.opensourcestats;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import de.lukaspanni.opensourcestats.analysis.ContributionCountCommitRanking;
import de.lukaspanni.opensourcestats.analysis.ContributionCountRanking;
import de.lukaspanni.opensourcestats.data.ContributionCount;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class ContributionCountCommitRankingUnitTest {

    @Test
    public void rank_sorted_by_commits(){
        List<ContributionCount> data = new ArrayList<>();
        ContributionCount element1 = new ContributionCount(2, 0, 0, 0);
        ContributionCount element2 = new ContributionCount(3, 0, 0, 0);
        ContributionCount element3 = new ContributionCount(1, 0, 0, 0);
        data.add(element1);
        data.add(element2);
        data.add(element3);
        ContributionCountRanking ranking = new ContributionCountCommitRanking();

        List<ContributionCount> ranked = ranking.rank(data);
        assertThat(ranked.get(0), is(equalTo(element2)));
        assertThat(ranked.get(1), is(equalTo(element1)));
        assertThat(ranked.get(2), is(equalTo(element3)));
    }

    @Test
    public void rank_sorted_by_commits_other_values_ignored(){
        List<ContributionCount> data = new ArrayList<>();
        ContributionCount element1 = new ContributionCount(1, 100, 50, 200);
        ContributionCount element2 = new ContributionCount(2, 0, 0, 0);
        data.add(element1);
        data.add(element2);
        ContributionCountRanking ranking = new ContributionCountCommitRanking();

        List<ContributionCount> ranked = ranking.rank(data);
        assertThat(ranked.get(0), is(equalTo(element2)));
        assertThat(ranked.get(1), is(equalTo(element1)));
    }

    @Test
    public void rank_multiple_calls_return_same_reference(){
        List<ContributionCount> data = new ArrayList<>();
        ContributionCount element1 = new ContributionCount(1, 0, 0, 0);
        ContributionCount element2 = new ContributionCount(2, 0, 0, 0);
        data.add(element1);
        data.add(element2);
        ContributionCountRanking ranking = new ContributionCountCommitRanking();

        List<ContributionCount> ranked1 = ranking.rank(data);
        List<ContributionCount> ranked2 = ranking.rank(data);

        assertThat(ranked1 == ranked2, is(true));
    }
}
