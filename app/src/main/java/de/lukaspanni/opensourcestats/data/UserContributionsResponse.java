package de.lukaspanni.opensourcestats.data;

import de.lukaspanni.opensourcestats.UserContributionsQuery;

import java.util.stream.Collectors;

/**
 * ResponseData for UserContributions-Requests, aggregate of ContributionCount and ContributionRepositories
 */
public class UserContributionsResponse extends ResponseData {

    private ContributionCount contributionCount;
    private ContributionRepositories contributionRepositories;

    public UserContributionsResponse(UserContributionsQuery.Viewer data) {
        this(data, null);
    }


    public UserContributionsResponse(UserContributionsQuery.Viewer data, TimeSpan timeSpan) {
        super(QueryType.USER_CONTRIBUTIONS_QUERY);
        contributionCount = new ContributionCount(
                data.contributionsCollection().totalCommitContributions(),
                data.contributionsCollection().totalIssueContributions(),
                data.contributionsCollection().totalPullRequestContributions(),
                data.contributionsCollection().totalPullRequestReviewContributions(),
                timeSpan
        );
        contributionRepositories = new ContributionRepositories(
                data.contributionsCollection().commitContributionsByRepository().stream().map(repo -> repo.repository().nameWithOwner()).collect(Collectors.toSet()),
                data.contributionsCollection().issueContributionsByRepository().stream().map(repo -> repo.repository().nameWithOwner()).collect(Collectors.toSet()),
                data.contributionsCollection().pullRequestContributionsByRepository().stream().map(repo -> repo.repository().nameWithOwner()).collect(Collectors.toSet()),
                data.contributionsCollection().pullRequestReviewContributionsByRepository().stream().map(repo -> repo.repository().nameWithOwner()).collect(Collectors.toSet()))
        ;
    }

    public ContributionCount getContributionCount() {
        return this.contributionCount;
    }

    public ContributionRepositories getContributionRepositories() {
        return this.contributionRepositories;
    }

}
