package de.lukaspanni.opensourcestats;

import org.junit.Test;

import de.lukaspanni.opensourcestats.data.ContributionCount;

import static org.junit.Assert.*;


public class ContributionCountUnitTest {

    @Test
    public void constructor_commits_smallerZero_correct() {
        ContributionCount testContributionCount = new ContributionCount(-2, 0, 0, 0);
        assertNotEquals(-2, testContributionCount.getCommitCount());
        assertEquals(0, testContributionCount.getCommitCount());
    }

    @Test
    public void constructor_issues_smallerZero_correct() {
        ContributionCount testContributionCount = new ContributionCount(0, -2, 0, 0);
        assertNotEquals(-2, testContributionCount.getIssueCount());
        assertEquals(0, testContributionCount.getIssueCount());
    }

    @Test
    public void constructor_pullRequests_smallerZero_correct() {
        ContributionCount testContributionCount = new ContributionCount(0, 0, -2, 0);
        assertNotEquals(-2, testContributionCount.getPullRequestCount());
        assertEquals(0, testContributionCount.getPullRequestCount());
    }
    @Test
    public void constructor_pullRequestReviews_smallerZero_correct() {
        ContributionCount testContributionCount = new ContributionCount(0, 0, 0,-2);
        assertNotEquals(-2, testContributionCount.getPullRequestReviewCount());
        assertEquals(0, testContributionCount.getPullRequestReviewCount());
    }
}
