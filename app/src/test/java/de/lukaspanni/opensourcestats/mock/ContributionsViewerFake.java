package de.lukaspanni.opensourcestats.mock;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;

import de.lukaspanni.opensourcestats.UserContributionsQuery;
import de.lukaspanni.opensourcestats.data.ContributionCount;
import de.lukaspanni.opensourcestats.data.ContributionType;

public class ContributionsViewerFake extends UserContributionsQuery.Viewer {

    public static ContributionsViewerFake create(String login, String name, ContributionCount contributionCount, Map<ContributionType, Set<String>> contributionRepositories) {
        UserContributionsQuery.ContributionsCollection contributionsCollection = ContributionsCollectionFake.create(contributionCount, contributionRepositories);
        return new ContributionsViewerFake(login, name, contributionsCollection);
    }

    public ContributionsViewerFake(@NotNull String login, @Nullable String name, @NotNull UserContributionsQuery.ContributionsCollection contributionsCollection) {
        super("Viewer", login, name, contributionsCollection);
    }

}
