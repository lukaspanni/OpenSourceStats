package de.lukaspanni.opensourcestats.analysis;

import java.util.List;
import java.util.stream.Collectors;

import de.lukaspanni.opensourcestats.data.ContributionCount;

public class ContributionCountCommitRanking extends ContributionCountRanking {

    private List<ContributionCount> lastInputList = null;
    private List<ContributionCount> lastRankedList = null;

    @Override
    public List<ContributionCount> rank(List<ContributionCount> inputList) {
        if (inputList != null && inputList.equals(lastInputList)) return lastRankedList;
        lastInputList = inputList;
        lastRankedList = lastInputList.stream()
                .sorted((o1, o2) -> Integer.compare(o2.getCommitCount(), o1.getCommitCount()))
                .collect(Collectors.toList());
        return lastRankedList;
    }

}
