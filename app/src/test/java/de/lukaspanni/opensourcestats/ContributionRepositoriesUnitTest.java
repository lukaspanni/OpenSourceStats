package de.lukaspanni.opensourcestats;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import de.lukaspanni.opensourcestats.data.ContributionRepositories;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;


public class ContributionRepositoriesUnitTest {

    @Test
    public void constructor_null_handling() {
        List<String> commitRepositories = new ArrayList<>();
        List<String> issueRepositories = new ArrayList<>();
        ContributionRepositories testRepositoriesObject = new ContributionRepositories(commitRepositories, issueRepositories, null, null);

        assertThat(testRepositoriesObject.getCommitRepositories(), is(commitRepositories));
        assertThat(testRepositoriesObject.getIssueRepositories(), is(issueRepositories));
        assertThat(testRepositoriesObject.getPullRequestRepositories(), is(notNullValue()));
        assertThat(testRepositoriesObject.getPullRequestReviewRepositories(), is(notNullValue()));
    }
}
