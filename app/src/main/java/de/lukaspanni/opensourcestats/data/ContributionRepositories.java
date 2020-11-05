package de.lukaspanni.opensourcestats.data;

import java.util.List;
import java.util.stream.Collectors;

import de.lukaspanni.opensourcestats.UserContributionsQuery;

public final class ContributionRepositories  {

    private final List<String> commitRepositories;
    private final List<String> issueRepositories;
    private final List<String> pullRequestRepositories;
    private final List<String> pullRequestReviewRepositories;

    public ContributionRepositories(List<String> commitRepositories, List<String> issueRepositories, List<String> pullRequestRepositories, List<String> pullRequestReviewRepositories) {
        this.commitRepositories = commitRepositories;
        this.issueRepositories = issueRepositories;
        this.pullRequestRepositories = pullRequestRepositories;
        this.pullRequestReviewRepositories = pullRequestReviewRepositories;
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
