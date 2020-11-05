package de.lukaspanni.opensourcestats.data;

/**
 * ContributionCount ValueObject, bundles commit, issue, pullrequest and pullrequestreview count
 */
public final class ContributionCount {

    private final int commitCount;
    private final int issueCount;
    private final int pullRequestCount;
    private final int pullRequestReviewCount;

    public ContributionCount(int commits, int issues, int pullRequests, int pullRequestReviews) {
        this.commitCount = Math.max(commits, 0);
        this.issueCount = Math.max(issues, 0);
        this.pullRequestCount = Math.max(pullRequests, 0);
        this.pullRequestReviewCount = Math.max(pullRequestReviews, 0);
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

    //TODO: compute progress to given ContributionCount-Object
}
