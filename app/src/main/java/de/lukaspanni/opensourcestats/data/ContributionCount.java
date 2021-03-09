package de.lukaspanni.opensourcestats.data;

import java.util.Objects;

/**
 * ContributionCount ValueObject, bundles commit, issue, pullrequest and pullrequestreview count
 */
public final class ContributionCount {

    private final TimeSpan contributionTimeSpan;
    private final int commitCount;
    private final int issueCount;
    private final int pullRequestCount;
    private final int pullRequestReviewCount;

    public ContributionCount(int commits, int issues, int pullRequests, int pullRequestReviews){
        this(commits, issues, pullRequests, pullRequestReviews, null);
    }

    public ContributionCount(int commits, int issues, int pullRequests, int pullRequestReviews, TimeSpan timeSpan) {
        this.commitCount = getPositiveCount(commits);
        this.issueCount = getPositiveCount(issues);
        this.pullRequestCount = getPositiveCount(pullRequests);
        this.pullRequestReviewCount = getPositiveCount(pullRequestReviews);
        this.contributionTimeSpan = timeSpan;
    }

    private int getPositiveCount(int count) {
        return Math.max(count, 0);
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

    public TimeSpan getContributionTimeSpan(){
        return contributionTimeSpan;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContributionCount that = (ContributionCount) o;
        if(contributionTimeSpan == null || that.getContributionTimeSpan() == null) {
            return getCommitCount() == that.getCommitCount() &&
                    getIssueCount() == that.getIssueCount() &&
                    getPullRequestCount() == that.getPullRequestCount() &&
                    getPullRequestReviewCount() == that.getPullRequestReviewCount();
        }
        return contributionTimeSpan.equals(that.getContributionTimeSpan());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCommitCount(), getIssueCount(), getPullRequestCount(), getPullRequestReviewCount());
    }
}
