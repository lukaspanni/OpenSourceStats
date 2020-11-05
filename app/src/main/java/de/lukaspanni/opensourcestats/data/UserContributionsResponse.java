package de.lukaspanni.opensourcestats.data;

import de.lukaspanni.opensourcestats.UserContributionsQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserContributionsResponse extends ResponseData {

    private ContributionCount contributionCount;
    private List<String> commitRepositories;
    private List<String> issueRepositories;
    private List<String> pullRequestRepositories;
    private List<String> pullRequestReviewRepositories;

    public UserContributionsResponse(UserContributionsQuery.Viewer data) {
        super(QueryType.USER_CONTRIBUTIONS_QUERY);
        contributionCount = new ContributionCount(
                data.contributionsCollection().totalCommitContributions(),
                data.contributionsCollection().totalIssueContributions(),
                data.contributionsCollection().totalPullRequestContributions(),
                data.contributionsCollection().totalPullRequestReviewContributions()
        );
        this.commitRepositories = data.contributionsCollection().commitContributionsByRepository().stream().map(repo -> repo.repository().nameWithOwner()).collect(Collectors.toList());
        this.issueRepositories = data.contributionsCollection().issueContributionsByRepository().stream().map(repo -> repo.repository().nameWithOwner()).collect(Collectors.toList());
        this.pullRequestRepositories = data.contributionsCollection().pullRequestContributionsByRepository().stream().map(repo -> repo.repository().nameWithOwner()).collect(Collectors.toList());
        this.pullRequestReviewRepositories = data.contributionsCollection().pullRequestReviewContributionsByRepository().stream().map(repo -> repo.repository().nameWithOwner()).collect(Collectors.toList());
    }

    public ContributionCount getContributionCount() {
        return this.contributionCount;
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
