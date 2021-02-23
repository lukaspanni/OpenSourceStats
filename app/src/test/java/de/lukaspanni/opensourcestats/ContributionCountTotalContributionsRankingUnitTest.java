package de.lukaspanni.opensourcestats;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import de.lukaspanni.opensourcestats.analysis.ContributionCountCommitRanking;
import de.lukaspanni.opensourcestats.analysis.ContributionCountRanking;
import de.lukaspanni.opensourcestats.analysis.ContributionCountTotalContributionsRanking;
import de.lukaspanni.opensourcestats.data.ContributionCount;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ContributionCountTotalContributionsRankingUnitTest {

    @Test
    public void rank_sorted_total_contributions(){
        List<ContributionCount> data = new ArrayList<>();
        ContributionCount element1 = new ContributionCount(3, 2, 1, 1);
        ContributionCount element2 = new ContributionCount(2, 1, 3, 2);
        ContributionCount element3 = new ContributionCount(0, 1, 1, 2);
        data.add(element1);
        data.add(element2);
        data.add(element3);
        ContributionCountRanking ranking = new ContributionCountTotalContributionsRanking();

        List<ContributionCount> ranked = ranking.rank(data);
        assertThat(ranked.get(0), is(equalTo(element2)));
        assertThat(ranked.get(1), is(equalTo(element1)));
        assertThat(ranked.get(2), is(equalTo(element3)));
    }

    @Test
    public void rank_multiple_calls_return_same_reference(){
        List<ContributionCount> data = new ArrayList<>();
        ContributionCount element1 = new ContributionCount(1, 3, 2, 1);
        ContributionCount element2 = new ContributionCount(2, 1, 3, 2);
        data.add(element1);
        data.add(element2);
        ContributionCountRanking ranking = new ContributionCountTotalContributionsRanking();

        List<ContributionCount> ranked1 = ranking.rank(data);
        List<ContributionCount> ranked2 = ranking.rank(data);

        assertThat(ranked1 == ranked2, is(true));
    }
}
