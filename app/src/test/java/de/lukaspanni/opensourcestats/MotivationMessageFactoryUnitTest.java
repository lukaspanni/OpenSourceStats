package de.lukaspanni.opensourcestats;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import de.lukaspanni.opensourcestats.data.ContributionCountChange;
import de.lukaspanni.opensourcestats.data.ContributionCount;
import de.lukaspanni.opensourcestats.mock.FakeStringResourceAccess;
import de.lukaspanni.opensourcestats.ui.StringResourceAccess;
import de.lukaspanni.opensourcestats.ui.progress.MotivationMessageFactory;
import de.lukaspanni.opensourcestats.ui.progress.MotivationMessage;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MotivationMessageFactoryUnitTest {

    @NotNull
    private ContributionCountChange getContributionCountChange(boolean positive) {
        int commits = 10;
        int issues = 5;
        int pullRequests = 4;
        int pullRequestReviews = 6;
        ContributionCount previousPeriod = new ContributionCount(commits, issues, pullRequests, pullRequestReviews);
        ContributionCount currentPeriod;
        if(positive)
             currentPeriod = new ContributionCount(commits+5, issues+1, pullRequests, pullRequestReviews+1);
        else
            currentPeriod = new ContributionCount(commits-5, issues-1, pullRequests, pullRequestReviews-1);
        return new ContributionCountChange(currentPeriod, previousPeriod);
    }

    @Test
    public void test_create_motivation_message_positive(){
        ContributionCountChange positiveChange = getContributionCountChange(true);

        StringResourceAccess stringResourceAccess = new FakeStringResourceAccess();
        MotivationMessageFactory factory = new MotivationMessageFactory(stringResourceAccess);
        MotivationMessage message = factory.createMotivationMessage(positiveChange);

        assertThat(message.getType(), is(equalTo(MotivationMessage.MOTIVATION_TYPE.KEEP_ON)));
    }

    @Test
    public void test_create_motivation_message_negative(){
        ContributionCountChange negativeChange = getContributionCountChange(false);

        StringResourceAccess stringResourceAccess = new FakeStringResourceAccess();
        MotivationMessageFactory factory = new MotivationMessageFactory(stringResourceAccess);
        MotivationMessage message = factory.createMotivationMessage(negativeChange);

        assertThat(message.getType(), is(equalTo(MotivationMessage.MOTIVATION_TYPE.WORK_HARDER)));
    }

}
