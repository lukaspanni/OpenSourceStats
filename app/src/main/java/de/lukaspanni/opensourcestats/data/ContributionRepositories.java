package de.lukaspanni.opensourcestats.data;

import java.util.ArrayList;
import java.util.List;


/**
 * ContributionRepositories ValueObject, bundles commit, issue, pullrequest and pullrequestreview repositories
 */
public final class ContributionRepositories  {

    private final List<String> commitRepositories;
    private final List<String> issueRepositories;
    private final List<String> pullRequestRepositories;
    private final List<String> pullRequestReviewRepositories;

    public ContributionRepositories(List<String> commitRepositories, List<String> issueRepositories, List<String> pullRequestRepositories, List<String> pullRequestReviewRepositories) {
        this.commitRepositories = (commitRepositories != null) ? commitRepositories : new ArrayList<>();
        this.issueRepositories = (issueRepositories != null) ? issueRepositories : new ArrayList<>();
        this.pullRequestRepositories = (pullRequestRepositories != null) ? pullRequestRepositories : new ArrayList<>();
        this.pullRequestReviewRepositories = (pullRequestReviewRepositories != null) ? pullRequestReviewRepositories : new ArrayList<>();
    }

    public List<String> getCommitRepositories() {
        return this.commitRepositories;
    }

    public List<String> getIssueRepositories() {
        return this.issueRepositories;
    }

    public List<String> getPullRequestRepositories() {
        return this.pullRequestRepositories;
    }

    public List<String> getPullRequestReviewRepositories() {
        return this.pullRequestReviewRepositories;
    }

}
