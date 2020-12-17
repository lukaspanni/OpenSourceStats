package de.lukaspanni.opensourcestats;

import org.junit.Test;

import de.lukaspanni.opensourcestats.data.ContributionCount;
import de.lukaspanni.opensourcestats.data.ContributionCountChange;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class ContributionCountChangeUnitTest {

    public static final double DELTA = 0.00001;

    @Test
    public void constructor_change_positive_correct() {
        ContributionCount previousPeriod = new ContributionCount(10, 0, 0, 0);
        ContributionCount currentPeriod = new ContributionCount(20, 0, 0, 0);
        ContributionCountChange change = new ContributionCountChange(currentPeriod, previousPeriod);
        assertEquals(1.0, change.getCommitCountChange(), DELTA);
    }

    @Test
    public void constructor_change_negative_correct() {
        ContributionCount previousPeriod = new ContributionCount(10, 0, 0, 0);
        ContributionCount currentPeriod = new ContributionCount(0, 0, 0, 0);
        ContributionCountChange change = new ContributionCountChange(currentPeriod, previousPeriod);
        assertEquals(-1.0, change.getCommitCountChange(), DELTA);
    }

}
