package de.lukaspanni.opensourcestats;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import de.lukaspanni.opensourcestats.analysis.ContributionCountCommitRanking;
import de.lukaspanni.opensourcestats.analysis.ContributionCountCustomWeightedRanking;
import de.lukaspanni.opensourcestats.analysis.ContributionCountRanking;
import de.lukaspanni.opensourcestats.data.ContributionCount;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ContributionCountCustomRankingUnitTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void rank_weights_to_big(){
        int commitWeight = 100;
        int issueWeight = 20;
        int pullRequestWeight = 20;
        int pullRequestReviewWeight = 60;

        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Total Weight (200) is not equal to 100");

        ContributionCountRanking ranking = new ContributionCountCustomWeightedRanking(commitWeight, issueWeight,pullRequestWeight,pullRequestReviewWeight);
    }

    @Test
    public void rank_weights_to_small(){
        int commitWeight = 10;
        int issueWeight = 10;
        int pullRequestWeight = 10;
        int pullRequestReviewWeight = 10;

        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Total Weight (40) is not equal to 100");

        ContributionCountRanking ranking = new ContributionCountCustomWeightedRanking(commitWeight, issueWeight,pullRequestWeight,pullRequestReviewWeight);
    }

    @Test
    public void rank_custom_sort_order(){
        int commitWeight = 10;
        int issueWeight = 20;
        int pullRequestWeight = 20;
        int pullRequestReviewWeight = 50;
        List<ContributionCount> data = new ArrayList<>();
        ContributionCount element1 = new ContributionCount(2, 3, 5, 0); //Weight: 180
        ContributionCount element2 = new ContributionCount(3, 5, 2, 4); //Weight: 370
        ContributionCount element3 = new ContributionCount(1, 3, 6, 2); //Weight: 290
        data.add(element1);
        data.add(element2);
        data.add(element3);
        ContributionCountRanking ranking = new ContributionCountCustomWeightedRanking(commitWeight, issueWeight,pullRequestWeight,pullRequestReviewWeight);


        List<ContributionCount> ranked = ranking.rank(data);
        assertThat(ranked.get(0), is(equalTo(element2)));
        assertThat(ranked.get(1), is(equalTo(element3)));
        assertThat(ranked.get(2), is(equalTo(element1)));
    }

    @Test
    public void rank_custom_sort_order_negative_weights(){
        int commitWeight = -50;
        int issueWeight = 50;
        int pullRequestWeight = 50;
        int pullRequestReviewWeight = 50;
        List<ContributionCount> data = new ArrayList<>();
        ContributionCount element1 = new ContributionCount(2, 3, 5, 0); //Weight: 300
        ContributionCount element2 = new ContributionCount(3, 5, 2, 4); //Weight: 400
        ContributionCount element3 = new ContributionCount(1, 3, 6, 2); //Weight: 500
        data.add(element1);
        data.add(element2);
        data.add(element3);
        ContributionCountRanking ranking = new ContributionCountCustomWeightedRanking(commitWeight, issueWeight,pullRequestWeight,pullRequestReviewWeight);


        List<ContributionCount> ranked = ranking.rank(data);
        assertThat(ranked.get(0), is(equalTo(element3)));
        assertThat(ranked.get(1), is(equalTo(element2)));
        assertThat(ranked.get(2), is(equalTo(element1)));
    }

}
