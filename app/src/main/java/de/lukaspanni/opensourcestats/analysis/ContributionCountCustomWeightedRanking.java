package de.lukaspanni.opensourcestats.analysis;

import java.util.List;
import java.util.stream.Collectors;

import de.lukaspanni.opensourcestats.data.ContributionCount;

public class ContributionCountCustomWeightedRanking extends ContributionCountRanking {

    private final int commitWeight;
    private final int issueWeight;
    private final int pullRequestWeight;
    private final int pullRequestReviewWeight;

    public ContributionCountCustomWeightedRanking(int commitWeight, int issueWeight, int pullRequestWeight, int pullRequestReviewWeight) {
        float totalWeight = commitWeight + issueWeight + pullRequestWeight + pullRequestReviewWeight;
        if (totalWeight != 100) {
            throw new IllegalArgumentException("Total Weight (" + totalWeight + ") is not equal to 100");
        }
        this.commitWeight = commitWeight;
        this.issueWeight = issueWeight;
        this.pullRequestWeight = pullRequestWeight;
        this.pullRequestReviewWeight = pullRequestReviewWeight;

    }

    private int calculateTotalWeight(ContributionCount contributionCount) {
        return ((commitWeight * contributionCount.getCommitCount())
                + (issueWeight * contributionCount.getIssueCount())
                + (pullRequestWeight * contributionCount.getPullRequestCount())
                + (pullRequestReviewWeight * contributionCount.getPullRequestReviewCount()));
    }

    @Override
    public List<ContributionCount> rank(List<ContributionCount> inputList) {
        List<ContributionCount> rankedList = inputList.stream()
                .sorted((o1, o2) -> Integer.compare(calculateTotalWeight(o2), calculateTotalWeight(o1)))
                .collect(Collectors.toList());
        return rankedList;
    }
}
