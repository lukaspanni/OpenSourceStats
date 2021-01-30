package de.lukaspanni.opensourcestats.data;

import java.util.Objects;

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

    public int getTotalContributions(){
        return commitCount + issueCount + pullRequestCount + pullRequestReviewCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContributionCount that = (ContributionCount) o;
        return getCommitCount() == that.getCommitCount() &&
                getIssueCount() == that.getIssueCount() &&
                getPullRequestCount() == that.getPullRequestCount() &&
                getPullRequestReviewCount() == that.getPullRequestReviewCount();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCommitCount(), getIssueCount(), getPullRequestCount(), getPullRequestReviewCount());
    }
}
