package de.lukaspanni.opensourcestats.mock;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import de.lukaspanni.opensourcestats.UserContributionsQuery;
import de.lukaspanni.opensourcestats.data.ContributionCount;
import de.lukaspanni.opensourcestats.data.ContributionType;

class ContributionsCollectionFake extends UserContributionsQuery.ContributionsCollection {
    public ContributionsCollectionFake(@NotNull Date startedAt, @NotNull Date endedAt, int totalCommitContributions, int totalIssueContributions, int totalPullRequestContributions, int totalPullRequestReviewContributions, int totalRepositoryContributions, @NotNull List<UserContributionsQuery.CommitContributionsByRepository> commitContributionsByRepository, @NotNull List<UserContributionsQuery.IssueContributionsByRepository> issueContributionsByRepository, @NotNull List<UserContributionsQuery.PullRequestContributionsByRepository> pullRequestContributionsByRepository, @NotNull List<UserContributionsQuery.PullRequestReviewContributionsByRepository> pullRequestReviewContributionsByRepository) {
        super("ContributionsCollection", startedAt, endedAt, totalCommitContributions, totalIssueContributions, totalPullRequestContributions, totalPullRequestReviewContributions, totalRepositoryContributions, commitContributionsByRepository, issueContributionsByRepository, pullRequestContributionsByRepository, pullRequestReviewContributionsByRepository);
    }

    public static UserContributionsQuery.ContributionsCollection create(ContributionCount contributionCount, Map<ContributionType, List<String>> contributionRepositories) {
        Date startedAt = new Date(120,12,19);
        Date endeddAt = new Date(120,12,20);


        final int commits = contributionCount.getCommitCount();
        final int issues = contributionCount.getIssueCount();
        final int pullRequests = contributionCount.getPullRequestCount();
        final int pullRequestReviews = contributionCount.getPullRequestReviewCount();
        List<UserContributionsQuery.CommitContributionsByRepository> commitContributions = new ArrayList<>();
        List<UserContributionsQuery.IssueContributionsByRepository> issueContributions = new ArrayList<>();
        List<UserContributionsQuery.PullRequestContributionsByRepository> pullRequestContributions = new ArrayList<>();
        List<UserContributionsQuery.PullRequestReviewContributionsByRepository> pullRequestReviewContributions = new ArrayList<>();

        for(Map.Entry<ContributionType, List<String>> entry : contributionRepositories.entrySet()) {
            ContributionType type = entry.getKey();
            if (type == ContributionType.COMMIT)
                entry.getValue().forEach(s -> commitContributions.add(new UserContributionsQuery.CommitContributionsByRepository("CommitContributionsByRepository", new UserContributionsQuery.Repository("Repository", s))));
            if (type == ContributionType.ISSUE)
                entry.getValue().forEach(s ->issueContributions.add(new UserContributionsQuery.IssueContributionsByRepository("IssueContributionsByRepository", new UserContributionsQuery.Repository1("Repository", s))));
            if (type == ContributionType.PULL_REQUEST)
                entry.getValue().forEach(s ->pullRequestContributions.add(new UserContributionsQuery.PullRequestContributionsByRepository("PullRequestContributionsByRepository", new UserContributionsQuery.Repository2("Repository", s))));
            if (type == ContributionType.PULL_REQUEST_REVIEW)
                entry.getValue().forEach(s ->pullRequestReviewContributions.add(new UserContributionsQuery.PullRequestReviewContributionsByRepository("PullRequestReviewContributionsByRepository", new UserContributionsQuery.Repository3("Repository", s))));
        }
        return new ContributionsCollectionFake(startedAt, endeddAt, commits, issues, pullRequests, pullRequestReviews,
                commits+issues+pullRequests+pullRequestReviews, commitContributions, issueContributions, pullRequestContributions, pullRequestReviewContributions);
    }
}
