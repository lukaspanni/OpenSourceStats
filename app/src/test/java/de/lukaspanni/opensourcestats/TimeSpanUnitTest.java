package de.lukaspanni.opensourcestats;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Date;

import de.lukaspanni.opensourcestats.data.TimeSpan;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


public class TimeSpanUnitTest {

    @Test
    public void constructor_Date_zeroDate(){
        int testYear = 20;
        int testMonth = 11;
        int testDateStart = 9;
        int testDateEnd = 12;
        Date start = new Date(testYear, testMonth, testDateStart, 16,5,15);
        Date end = new Date(testYear,testMonth,testDateEnd,16,5,15);
        TimeSpan testTimeSpan = new TimeSpan(start, end);
        Date testTimeSpanStart = testTimeSpan.getStart();
        Date testTimeSpanEnd = testTimeSpan.getEnd();
        assertThat(testTimeSpanStart.getYear(), is(testYear));
        assertThat(testTimeSpanStart.getMonth(), is(testMonth));
        assertThat(testTimeSpanStart.getDate(), is(testDateStart));
        assertThat(testTimeSpanStart.getHours(), is(0));
        assertThat(testTimeSpanStart.getMinutes(), is(0));
        assertThat(testTimeSpanStart.getSeconds(), is(0));
        //Milliseconds are ignored!
        assertThat(testTimeSpanEnd.getYear(), is(testYear));
        assertThat(testTimeSpanEnd.getMonth(), is(testMonth));
        assertThat(testTimeSpanEnd.getDate(), is(testDateEnd));
        assertThat(testTimeSpanEnd.getHours(), is(0));
        assertThat(testTimeSpanEnd.getMinutes(), is(0));
        assertThat(testTimeSpanEnd.getSeconds(), is(0));
        //again: Milliseconds ignored!
    }

    @Test
    public void object_equals(){
        int testYear = 20;
        int testMonth = 11;
        int testDateStart = 9;
        int testDateEnd = 12;
        Date start = new Date(testYear, testMonth, testDateStart, 16,5,15);
        Date end = new Date(testYear,testMonth,testDateEnd,16,5,15);
        TimeSpan testTimeSpan = new TimeSpan(start, end);
        TimeSpan testTimeSpan2 = new TimeSpan(start, end);
        assertThat(testTimeSpan2.equals(testTimeSpan), is(true));
    }

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void test_end_smaller_start_exception(){
        int testYear = 20;
        int testMonth = 11;
        int testDateStart = 9;
        int testDateEnd = 8;
        Date start = new Date(testYear, testMonth, testDateStart, 16,5,15);
        Date end = new Date(testYear,testMonth,testDateEnd,16,5,15);

        expectedException.expect(IllegalArgumentException.class);
        TimeSpan testTimeSpan = new TimeSpan(start, end);
    }

}
