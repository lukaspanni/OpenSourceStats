package de.lukaspanni.opensourcestats;

import org.junit.Test;

import de.lukaspanni.opensourcestats.data.ContributionCount;
import de.lukaspanni.opensourcestats.data.ContributionCountChange;

import static org.junit.Assert.assertEquals;


public class ContributionCountChangeUnitTest {

    public static final double DELTA = 0.00001;

    @Test
    public void constructor_change_positive_correct() {
        ContributionCount previousPeriod = new ContributionCount(10, 0, 0, 0);
        ContributionCount currentPeriod = new ContributionCount(20, 0, 0, 0);

        ContributionCountChange change = new ContributionCountChange(currentPeriod, previousPeriod);

        assertEquals(1.0, change.getCommitCountChange(), DELTA);
    }

    @Test
    public void constructor_change_negative_correct() {
        ContributionCount previousPeriod = new ContributionCount(10, 0, 0, 0);
        ContributionCount currentPeriod = new ContributionCount(0, 0, 0, 0);

        ContributionCountChange change = new ContributionCountChange(currentPeriod, previousPeriod);

        assertEquals(-1.0, change.getCommitCountChange(), DELTA);
    }

    @Test
    public void constructor_other_changes_correct() {
        int commits = 100;
        int issues = 100;
        int pullRequests = 100;
        int pullRequestReviews = 100;
        double commitChange = 1.5;
        double issueChange = 0.8;
        double pullRequestChange = 0.5;
        double pullRequestReviewChange = 0.2;
        ContributionCount previousPeriod = new ContributionCount(commits, issues, pullRequests, pullRequestReviews);
        ContributionCount currentPeriod = new ContributionCount((int) (commits * commitChange), (int) (issues * issueChange), (int) (pullRequests * pullRequestChange), (int) (pullRequestReviews * pullRequestReviewChange));

        ContributionCountChange change = new ContributionCountChange(currentPeriod, previousPeriod);

        assertEquals(commitChange-1, change.getCommitCountChange(), DELTA);
        assertEquals(issueChange-1, change.getIssueCountChange(), DELTA);
        assertEquals(pullRequestChange-1, change.getPullRequestCountChange(), DELTA);
        assertEquals(pullRequestReviewChange-1, change.getPullRequestReviewCountChange(), DELTA);
    }

}
