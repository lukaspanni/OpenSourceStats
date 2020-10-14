package de.lukaspanni.opensourcestats.client;

public class ContributionCount {

    private final int commitCount;
    private final int issueCount;
    private final int pullRequestCount;
    private final int pullRequestReviewCount;

    public ContributionCount(int commits, int issues, int pullRequests, int pullRequestReviews){
        this.commitCount = commits;
        this.issueCount = issues;
        this.pullRequestCount = pullRequests;
        this.pullRequestReviewCount = pullRequestReviews;
    }

    public int getCommitCount() {
        return commitCount;
    }

    public int getIssueCount() {
        return issueCount;
    }

    public int getPullRequestCount() {
        return pullRequestCount;
    }

    public int getPullRequestReviewCount() {
        return pullRequestReviewCount;
    }
}
