package de.lukaspanni.opensourcestats;

import org.junit.Test;

import java.util.Date;

import de.lukaspanni.opensourcestats.util.TimeSpan;

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
        assertEquals(testYear, testTimeSpanStart.getYear());
        assertEquals(testMonth, testTimeSpanStart.getMonth());
        assertEquals(testDateStart, testTimeSpanStart.getDate());
        assertEquals(0, testTimeSpanStart.getHours());
        assertEquals(0, testTimeSpanStart.getMinutes());
        assertEquals(0, testTimeSpanStart.getSeconds());
        //Milliseconds are ignored!
        assertEquals(testYear, testTimeSpanEnd.getYear());
        assertEquals(testMonth, testTimeSpanEnd.getMonth());
        assertEquals(testDateEnd, testTimeSpanEnd.getDate());
        assertEquals(0, testTimeSpanEnd.getHours());
        assertEquals(0, testTimeSpanEnd.getMinutes());
        assertEquals(0, testTimeSpanEnd.getSeconds());
        //again: Milliseconds ignored!
    }

}
