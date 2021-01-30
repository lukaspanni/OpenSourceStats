package de.lukaspanni.opensourcestats.data;


/**
 * ContributionCount ValueObject, bundles commit, issue, pullrequest and pullrequestreview count changes
 */
public class ContributionCountChange {
    private final ContributionCount previousPeriod;
    private final ContributionCount currentPeriod;

    public ContributionCountChange(ContributionCount currentPeriod, ContributionCount previousPeriod) {
        this.currentPeriod = currentPeriod;
        this.previousPeriod = previousPeriod;
    }

    public float getCommitCountChange() {
        return calculateGain(currentPeriod.getCommitCount(), previousPeriod.getCommitCount());
    }

    public float getIssueCountChange() {
        return calculateGain(currentPeriod.getIssueCount(), previousPeriod.getIssueCount());
    }

    public float getPullRequestCountChange() {
        return calculateGain(currentPeriod.getPullRequestCount(), previousPeriod.getPullRequestCount());
    }

    public float getPullRequestReviewCountChange() {
        return calculateGain(currentPeriod.getPullRequestReviewCount(), previousPeriod.getPullRequestReviewCount());
    }

    private float calculateGain(float current, float old) {
        if (old == 0) return 0;
        return (current / (float) old) - 1;
    }

    public boolean isPositive() {
        //Positive if average change is >= 0% (no-change is considered positive)
        return currentPeriod.getTotalContributions() >= previousPeriod.getTotalContributions();
    }
}
