package de.lukaspanni.opensourcestats;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import de.lukaspanni.opensourcestats.data.ContributionRepositories;

import static org.junit.Assert.*;


public class ContributionRepositoriesUnitTest {

    @Test
    public void constructor_null_handling() {
        List<String> commitRepositories = new ArrayList<>();
        List<String> issueRepositories = new ArrayList<>();
        ContributionRepositories testRepositoriesObject = new ContributionRepositories(commitRepositories, issueRepositories, null, null);
        assertEquals(commitRepositories, testRepositoriesObject.getCommitRepositories());
        assertEquals(issueRepositories, testRepositoriesObject.getIssueRepositories());
        assertNotNull(testRepositoriesObject.getPullRequestRepositories());
        assertNotNull(testRepositoriesObject.getPullRequestReviewRepositories());
    }
}
