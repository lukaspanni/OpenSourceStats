package de.lukaspanni.opensourcestats.data;

import java.util.HashSet;
import java.util.Set;


/**
 * ContributionRepositories ValueObject, bundles commit, issue, pullrequest and pullrequestreview repositories
 */
public final class ContributionRepositories {

    private final Set<String> commitRepositories;
    private final Set<String> issueRepositories;
    private final Set<String> pullRequestRepositories;
    private final Set<String> pullRequestReviewRepositories;

    public ContributionRepositories(Set<String> commitRepositories, Set<String> issueRepositories, Set<String> pullRequestRepositories, Set<String> pullRequestReviewRepositories) {
        this.commitRepositories = (commitRepositories != null) ? commitRepositories : new HashSet<>();
        this.issueRepositories = (issueRepositories != null) ? issueRepositories : new HashSet<>();
        this.pullRequestRepositories = (pullRequestRepositories != null) ? pullRequestRepositories : new HashSet<>();
        this.pullRequestReviewRepositories = (pullRequestReviewRepositories != null) ? pullRequestReviewRepositories : new HashSet<>();
    }

    public Set<String> getCommitRepositories() {
        return this.commitRepositories;
    }

    public Set<String> getIssueRepositories() {
        return this.issueRepositories;
    }

    public Set<String> getPullRequestRepositories() {
        return this.pullRequestRepositories;
    }

    public Set<String> getPullRequestReviewRepositories() {
        return this.pullRequestReviewRepositories;
    }

}
