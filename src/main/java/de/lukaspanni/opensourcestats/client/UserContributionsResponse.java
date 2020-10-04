package de.lukaspanni.opensourcestats.client;

import de.lukaspanni.opensourcestats.UserContributionsQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserContributionsResponse extends ResponseData{

    private int commits;
    private int issues;
    private int pullRequests;
    private int pullRequestReviews;
    private List<String> commitRepositories;
    private List<String> issueRepositories;
    private List<String> pullRequestRepositories;
    private List<String> pullRequestReviewRepositories;

    public UserContributionsResponse( UserContributionsQuery.Viewer data) {
        super(QueryType.USER_CONTRIBUTIONS_QUERY);
        this.commits = data.contributionsCollection().totalCommitContributions();
        this.issues = data.contributionsCollection().totalIssueContributions();
        this.pullRequests = data.contributionsCollection().totalPullRequestContributions();
        this.pullRequestReviews = data.contributionsCollection().totalPullRequestReviewContributions();
        this.commitRepositories = data.contributionsCollection().commitContributionsByRepository().stream().map(repo -> repo.repository().nameWithOwner()).collect(Collectors.toList());
        this.issueRepositories = data.contributionsCollection().issueContributionsByRepository().stream().map(repo -> repo.repository().nameWithOwner()).collect(Collectors.toList());
        this.pullRequestRepositories = data.contributionsCollection().pullRequestContributionsByRepository().stream().map(repo -> repo.repository().nameWithOwner()).collect(Collectors.toList());
        this.pullRequestReviewRepositories = data.contributionsCollection().pullRequestReviewContributionsByRepository().stream().map(repo -> repo.repository().nameWithOwner()).collect(Collectors.toList());
    }

    public int getCommits() {
        return this.commits;
    }

    public int getIssues() {
        return this.issues;
    }

    public int getPullRequests() {
        return this.pullRequests;
    }

    public int getPullRequestReviews() {
        return this.pullRequestReviews;
    }


    public int getAllContributions() {
        return this.commits + this.issues + this.pullRequests + this.pullRequestReviews;
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

    public List<String> getAllContributionsRepositories() {
        List<String> ret = new ArrayList<>(this.commitRepositories);
        ret.addAll(this.issueRepositories);
        ret.addAll(this.pullRequestRepositories);
        ret.addAll(this.pullRequestReviewRepositories);
        return ret;
    }
}
