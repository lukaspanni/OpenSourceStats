package de.lukaspanni.opensourcestats.data;


/**
 * ContributionCount ValueObject, bundles commit, issue, pullrequest and pullrequestreview count changes
 */
public class ContributionCountChange {
    private final float commitCountChange;
    private final float issueCountChange;
    private final float pullRequestCountChange;
    private final float pullRequestReviewCountChange;

    public ContributionCountChange(ContributionCount currentPeriod, ContributionCount previousPeriod) {
        commitCountChange = calculateGain(currentPeriod.getCommitCount(), previousPeriod.getCommitCount());
        issueCountChange = calculateGain(currentPeriod.getIssueCount(), previousPeriod.getIssueCount());
        pullRequestCountChange = calculateGain(currentPeriod.getPullRequestCount(), previousPeriod.getPullRequestCount());
        pullRequestReviewCountChange = calculateGain(currentPeriod.getPullRequestReviewCount(), previousPeriod.getPullRequestReviewCount());
    }

    public float getCommitCountChange() {
        return commitCountChange;
    }

    public float getIssueCountChange() {
        return issueCountChange;
    }

    public float getPullRequestCountChange() {
        return pullRequestCountChange;
    }

    public float getPullRequestReviewCountChange() {
        return pullRequestReviewCountChange;
    }

    private float calculateGain(float current, float old) {
        if (old == 0) return 0;
        return (current / (float) old) - 1;
    }
}
