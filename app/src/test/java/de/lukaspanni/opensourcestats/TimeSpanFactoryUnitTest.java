package de.lukaspanni.opensourcestats;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import de.lukaspanni.opensourcestats.data.TimeSpan;
import de.lukaspanni.opensourcestats.data.TimeSpanFactory;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TimeSpanFactoryUnitTest {

    private static final int DATE_YEAR_OFFSET = 1900;
    private final int defaultMonth = 0;
    private final int defaultDay = 4;
    private final int defaultYear = 2021;

    private Calendar generateTestCalendar() {
        Locale locale = new Locale("de", "DE");
        Locale.setDefault(locale);
        Calendar c = new GregorianCalendar(defaultYear, defaultMonth, defaultDay);
        c.setFirstDayOfWeek(Calendar.MONDAY);
        return c;
    }

    @Test
    public void test_get_current_week() {
        Calendar testCalendar = generateTestCalendar();
        Date currentDate = new Date();
        Date expectedStart = new Date(defaultYear - DATE_YEAR_OFFSET, currentDate.getMonth(), currentDate.getDate() - (currentDate.getDay() - 1));
        Date expectedEnd = new Date(defaultYear - DATE_YEAR_OFFSET, currentDate.getMonth(), currentDate.getDate() - (currentDate.getDay() - 1) + 6);

        TimeSpan ts = TimeSpanFactory.getCurrentWeek(testCalendar);

        assertThat(ts.getStart(), is(equalTo(expectedStart)));
        assertThat(ts.getEnd(), is(equalTo(expectedEnd)));
    }

    @Test
    public void test_get_last_week() {
        Calendar testCalendar = generateTestCalendar();
        Date expectedStart = new Date(defaultYear - DATE_YEAR_OFFSET, defaultMonth, defaultDay - 7);
        Date expectedEnd = new Date(defaultYear - DATE_YEAR_OFFSET, defaultMonth, defaultDay - 1);

        TimeSpan ts = TimeSpanFactory.getLastWeek(testCalendar);

        assertThat(ts.getStart(), is(equalTo(expectedStart)));
        assertThat(ts.getEnd(), is(equalTo(expectedEnd)));
    }

    @Test
    public void test_get_week() {
        Calendar testCalendar = generateTestCalendar();
        Date dayInWeek = new Date(defaultYear - DATE_YEAR_OFFSET, defaultMonth, defaultDay+2);
        Date expectedStart = new Date(defaultYear - DATE_YEAR_OFFSET, defaultMonth, defaultDay);
        Date expectedEnd = new Date(defaultYear - DATE_YEAR_OFFSET, defaultMonth, defaultDay + 6);

        TimeSpan ts = TimeSpanFactory.getWeek(testCalendar, dayInWeek);

         assertThat(ts.getStart(), is(equalTo(expectedStart)));
         assertThat(ts.getEnd(), is(equalTo(expectedEnd)));
    }

    @Test
    public void test_get_month() {
        Calendar testCalendar = generateTestCalendar();
        Date dayInMonth = new Date(defaultYear - DATE_YEAR_OFFSET, defaultMonth, defaultDay+5);
        Date expectedStart = new Date(defaultYear - DATE_YEAR_OFFSET, defaultMonth, 1);
        Date expectedEnd = new Date(defaultYear - DATE_YEAR_OFFSET, defaultMonth+1, 0);

        TimeSpan ts = TimeSpanFactory.getMonth(testCalendar, dayInMonth);

        assertThat(ts.getStart(), is(equalTo(expectedStart)));
        assertThat(ts.getEnd(), is(equalTo(expectedEnd)));
    }

    @Test
    public void test_get_current_month() {
        Calendar testCalendar = generateTestCalendar();
        Date currentDate = new Date();
        Date expectedStart = new Date(defaultYear - DATE_YEAR_OFFSET, currentDate.getMonth(), 1);
        Date expectedEnd = new Date(defaultYear - DATE_YEAR_OFFSET, currentDate.getMonth() + 1, 0);

        TimeSpan ts = TimeSpanFactory.getCurrentMonth(testCalendar);

        assertThat(ts.getStart(), is(equalTo(expectedStart)));
        assertThat(ts.getEnd(), is(equalTo(expectedEnd)));
    }

    @Test
    public void test_get_last_month() {
        Calendar testCalendar = generateTestCalendar();
        Date expectedStart = new Date(defaultYear - DATE_YEAR_OFFSET, defaultMonth - 1, 1);
        Date expectedEnd = new Date(defaultYear - DATE_YEAR_OFFSET, defaultMonth, 0);

        TimeSpan ts = TimeSpanFactory.getLastMonth(testCalendar);

        assertThat(ts.getStart(), is(equalTo(expectedStart)));
        assertThat(ts.getEnd(), is(equalTo(expectedEnd)));
    }

}
