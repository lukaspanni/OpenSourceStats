package de.lukaspanni.opensourcestats;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


import java.util.ArrayList;
import java.util.HashMap;

import de.lukaspanni.opensourcestats.analysis.ContributionCountAnalyzer;
import de.lukaspanni.opensourcestats.data.ContributionCount;
import de.lukaspanni.opensourcestats.data.TimeSpan;
import de.lukaspanni.opensourcestats.data.TimeSpanFactory;
import de.lukaspanni.opensourcestats.data.UserContributionsResponse;
import de.lukaspanni.opensourcestats.mock.ContributionsViewerFake;
import de.lukaspanni.opensourcestats.mock.UserContributionsRepositoryFake;


import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class ContributionCountAnalyzerUnitTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void getTopK_k_greater_collection(){
        ContributionsViewerFake testData = ContributionsViewerFake.create("test", "test", new ContributionCount(10, 5, 2,3), new HashMap());
        ArrayList<UserContributionsResponse> responses = new ArrayList<>();
        responses.add(new UserContributionsResponse(testData));
        UserContributionsRepositoryFake repositoryFake = new UserContributionsRepositoryFake(responses);
        TimeSpan timeSpan = TimeSpanFactory.getCurrentWeek();
        ContributionCountAnalyzer analyzer = new ContributionCountAnalyzer(repositoryFake, timeSpan);

        assertThat(analyzer.isReady(), is(true));
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Not enough data provided. Cannot get top 7 Elements.");
        analyzer.getTopK(null, 7);
    }

    @Test
    public void getTopK_data_not_loaded(){
        UserContributionsRepositoryFake repositoryFake = new UserContributionsRepositoryFake();
        TimeSpan timeSpan = TimeSpanFactory.getCurrentWeek();
        ContributionCountAnalyzer analyzer = new ContributionCountAnalyzer(repositoryFake, timeSpan);

        assertThat(analyzer.isReady(), is(false));
        exceptionRule.expect(IllegalStateException.class);
        exceptionRule.expectMessage("Not enough data loaded to execute this action");
        analyzer.getTopK(null, 1);
    }

}
