package de.lukaspanni.opensourcestats.util;

import java.util.Calendar;
import java.util.Date;

//TODO: Write Tests
public class DateUtility {

    public static Date zeroDate(Date date){
        Calendar calender = Calendar.getInstance();
        calender.setTime(date);
        calender.set(Calendar.HOUR_OF_DAY, 0);
        calender.set(Calendar.MINUTE, 0);
        calender.set(Calendar.SECOND, 0);
        calender.set(Calendar.MILLISECOND, 0);

        return calender.getTime();
    }

    public static TimeSpan getCurrentWeek(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        Date weekStart = calendar.getTime();
        calendar.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEnd = calendar.getTime();
        return new TimeSpan(weekStart, weekEnd);
    }

    public static TimeSpan getLastWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        Date weekStart = calendar.getTime();
        calendar.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEnd = calendar.getTime();
        return new TimeSpan(weekStart, weekEnd);
    }

    public static TimeSpan getWeek(Date dayInWeek){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dayInWeek);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        Date weekStart = calendar.getTime();
        calendar.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEnd = calendar.getTime();
        return new TimeSpan(weekStart, weekEnd);
    }

    public static TimeSpan getCurrentMonth(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date monthStart = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH)-1);
        Date monthEnd = calendar.getTime();
        return new TimeSpan(monthStart, monthEnd);
    }

    public static TimeSpan getLastMonth(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, -1);
        Date monthStart = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH)-1);
        Date monthEnd = calendar.getTime();
        return new TimeSpan(monthStart, monthEnd);
    }

    public static TimeSpan getMonth(Date dayInMonth){
        Calendar cal = Calendar.getInstance();
        cal.setTime(dayInMonth);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date monthStart = cal.getTime();
        cal.add(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH)-1);
        Date monthEnd = cal.getTime();
        return new TimeSpan(monthStart, monthEnd);
    }
}
