package de.lukaspanni.opensourcestats;

import org.junit.Test;

import de.lukaspanni.opensourcestats.data.ContributionCount;
import de.lukaspanni.opensourcestats.data.TimeSpan;
import de.lukaspanni.opensourcestats.data.TimeSpanFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;


public class ContributionCountUnitTest {

    @Test
    public void constructor_commits_smallerZero_correct() {
        ContributionCount testContributionCount = new ContributionCount(-2, 0, 0, 0);
        assertThat(testContributionCount.getCommitCount(), is(not(-2)));
        assertThat(testContributionCount.getCommitCount(), is(0));
    }

    @Test
    public void constructor_issues_smallerZero_correct() {
        ContributionCount testContributionCount = new ContributionCount(0, -2, 0, 0);
        assertThat(testContributionCount.getIssueCount(), is(not(-2)));
        assertThat(testContributionCount.getIssueCount(), is(0));
    }

    @Test
    public void constructor_pullRequests_smallerZero_correct() {
        ContributionCount testContributionCount = new ContributionCount(0, 0, -2, 0);
        assertThat(testContributionCount.getPullRequestCount(), is(not(-2)));
        assertThat(testContributionCount.getPullRequestCount(), is(0));
    }
    @Test
    public void constructor_pullRequestReviews_smallerZero_correct() {
        ContributionCount testContributionCount = new ContributionCount(0, 0, 0,-2);
        assertThat(testContributionCount.getPullRequestReviewCount(), is(not(-2)));
        assertThat(testContributionCount.getPullRequestReviewCount(), is(0));
    }

    @Test
    public void object_equal(){
        int commits = 10;
        int issues = 5;
        int pullRequests = 3;
        int pullRequestReviews = 6;
        ContributionCount testObject1 = new ContributionCount(commits, issues, pullRequests, pullRequestReviews);
        ContributionCount testObject2 = new ContributionCount(commits, issues, pullRequests, pullRequestReviews);
        assertThat(testObject2.equals(testObject1), is(true));
    }

    @Test
    public void object_not_equal(){
        int commits = 10;
        int issues = 5;
        int pullRequests = 3;
        int pullRequestReviews = 6;
        ContributionCount testObject1 = new ContributionCount(commits, issues, pullRequests, pullRequestReviews);
        ContributionCount testObject2 = new ContributionCount(commits+1, issues+1, pullRequests+1, pullRequestReviews+1);
        assertThat(testObject2.equals(testObject1), is(false));
    }

    @Test
    public void object_equal_with_time_span(){
        int commits = 10;
        int issues = 5;
        int pullRequests = 3;
        int pullRequestReviews = 6;
        TimeSpan timeSpan = TimeSpanFactory.getCurrentWeek();
        ContributionCount testObject1 = new ContributionCount(commits, issues, pullRequests, pullRequestReviews, timeSpan);
        ContributionCount testObject2 = new ContributionCount(commits+1, issues+1, pullRequests+1, pullRequestReviews+1, timeSpan);
        assertThat(testObject2.equals(testObject1), is(true));
    }

    @Test
    public void object_equal_with_one_time_span(){
        int commits = 10;
        int issues = 5;
        int pullRequests = 3;
        int pullRequestReviews = 6;
        TimeSpan timeSpan = TimeSpanFactory.getCurrentWeek();
        ContributionCount testObject1 = new ContributionCount(commits, issues, pullRequests, pullRequestReviews, timeSpan);
        ContributionCount testObject2 = new ContributionCount(commits, issues, pullRequests, pullRequestReviews);
        assertThat(testObject2.equals(testObject1), is(true));
    }

    @Test
    public void object_not_equal_with_time_span(){
        int commits = 10;
        int issues = 5;
        int pullRequests = 3;
        int pullRequestReviews = 6;
        TimeSpan timeSpan = TimeSpanFactory.getCurrentWeek();
        TimeSpan timeSpan2 = TimeSpanFactory.getLastWeek();
        ContributionCount testObject1 = new ContributionCount(commits, issues, pullRequests, pullRequestReviews, timeSpan);
        ContributionCount testObject2 = new ContributionCount(commits, issues, pullRequests, pullRequestReviews, timeSpan2);
        assertThat(testObject2.equals(testObject1), is(false));
    }
}
