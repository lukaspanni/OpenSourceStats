package de.lukaspanni.opensourcestats.analysis;

import java.util.List;

import de.lukaspanni.opensourcestats.data.ContributionCount;

public abstract class ContributionCountRanking {

    public abstract List<ContributionCount> rank(List<ContributionCount> inputList);

}
