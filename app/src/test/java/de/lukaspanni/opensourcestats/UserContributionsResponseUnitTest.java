package de.lukaspanni.opensourcestats;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.lukaspanni.opensourcestats.data.ContributionCount;
import de.lukaspanni.opensourcestats.data.ContributionType;
import de.lukaspanni.opensourcestats.data.UserContributionsResponse;
import de.lukaspanni.opensourcestats.mock.ContributionsViewerFake;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class UserContributionsResponseUnitTest {

    @Test
    public void constructor_repositories_correct() {
        List<String> commitRepositories = new ArrayList<>();
        List<String> issueRepositories = new ArrayList<>();
        List<String> pullRequestRepositories = new ArrayList<>();
        List<String> pullRequestReviewRepositories = new ArrayList<>();
        commitRepositories.add("repoOwner1/testCommitRepository1");
        commitRepositories.add("repoOwner1/testCommitRepository2");
        issueRepositories.add("repoOwner2/testIssueRepository1");
        issueRepositories.add("repoOwner1/testCommitRepository1");
        pullRequestRepositories.add("repoOwner3/testPRRepo");
        pullRequestReviewRepositories.add("repoOwner1/testPRRRepo");

        HashMap<ContributionType, List<String>> contributionRepositories = new HashMap<>();
        contributionRepositories.put(ContributionType.COMMIT, commitRepositories);
        contributionRepositories.put(ContributionType.ISSUE, issueRepositories);
        contributionRepositories.put(ContributionType.PULL_REQUEST, pullRequestRepositories);
        contributionRepositories.put(ContributionType.PULL_REQUEST_REVIEW, pullRequestReviewRepositories);
        final ContributionsViewerFake testData = ContributionsViewerFake.create("test", "test", new ContributionCount(10, 5, 2,3), contributionRepositories);
        UserContributionsResponse testResponse = new UserContributionsResponse(testData);

        assertThat(testResponse.getContributionRepositories().getCommitRepositories(), is(equalTo(commitRepositories)));
        assertThat(testResponse.getContributionRepositories().getIssueRepositories(), is(equalTo(issueRepositories)));
        assertThat(testResponse.getContributionRepositories().getPullRequestRepositories(), is(equalTo(pullRequestRepositories)));
        assertThat(testResponse.getContributionRepositories().getPullRequestReviewRepositories(), is(equalTo(pullRequestReviewRepositories)));
    }


}


