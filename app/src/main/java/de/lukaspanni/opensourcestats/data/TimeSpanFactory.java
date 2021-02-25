package de.lukaspanni.opensourcestats.data;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeSpanFactory {

    public static TimeSpan getCurrentWeek(){
        return getCurrentWeek(Calendar.getInstance());
    }

    public static TimeSpan getLastWeek(){
        return getLastWeek(Calendar.getInstance());
    }

    public static TimeSpan getCurrentMonth(){
        return getCurrentMonth(Calendar.getInstance());
    }

    public static TimeSpan getLastMonth(){
        return getLastMonth(Calendar.getInstance());
    }

    public static TimeSpan getWeek(Date dayInWeek){
        return getWeek(Calendar.getInstance(), dayInWeek);
    }

    public static TimeSpan getMonth(Date dayInMonth){
        return getMonth(Calendar.getInstance(), dayInMonth);
    }

    public static TimeSpan getCurrentWeek(Calendar calendar){
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        Date weekStart = calendar.getTime();
        calendar.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEnd = calendar.getTime();
        return new TimeSpan(weekStart, weekEnd);
    }

    public static TimeSpan getLastWeek(Calendar calendar) {
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        Date weekStart = calendar.getTime();
        calendar.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEnd = calendar.getTime();
        return new TimeSpan(weekStart, weekEnd);
    }

    public static TimeSpan getWeek(Calendar calendar, Date dayInWeek){
        calendar.setTime(dayInWeek);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        Date weekStart = calendar.getTime();
        calendar.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEnd = calendar.getTime();
        return new TimeSpan(weekStart, weekEnd);
    }

    public static TimeSpan getCurrentMonth(Calendar calendar){
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date monthStart = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH)-1);
        Date monthEnd = calendar.getTime();
        return new TimeSpan(monthStart, monthEnd);
    }

    public static TimeSpan getLastMonth(Calendar calendar){
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, -1);
        Date monthStart = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH)-1);
        Date monthEnd = calendar.getTime();
        return new TimeSpan(monthStart, monthEnd);
    }

    public static TimeSpan getMonth(Calendar calendar, Date dayInMonth){
        calendar.setTime(dayInMonth);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date monthStart = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH)-1);
        Date monthEnd = calendar.getTime();
        return new TimeSpan(monthStart, monthEnd);
    }

}
