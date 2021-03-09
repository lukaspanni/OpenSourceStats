package de.lukaspanni.opensourcestats.data;

import org.jetbrains.annotations.NotNull;

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
        return getWeek(new Date());
    }

    public static TimeSpan getLastWeek(Calendar calendar) {
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        return getWeekTimeSpan(calendar);
    }

    public static TimeSpan getWeek(Calendar calendar, Date dayInWeek){
        calendar.setTime(dayInWeek);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        return getWeekTimeSpan(calendar);
    }

    @NotNull
    private static TimeSpan getWeekTimeSpan(Calendar calendar) {
        Date weekStart = calendar.getTime();
        calendar.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEnd = calendar.getTime();
        return new TimeSpan(weekStart, weekEnd);
    }

    public static TimeSpan getCurrentMonth(Calendar calendar){
        return getMonth(new Date());
    }

    public static TimeSpan getLastMonth(Calendar calendar){
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, -1);
        return getMonthTimeSpan(calendar, calendar.getActualMaximum(Calendar.DAY_OF_MONTH) - 1);
    }

    public static TimeSpan getMonth(Calendar calendar, Date dayInMonth){
        calendar.setTime(dayInMonth);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return getMonthTimeSpan(calendar, calendar.getActualMaximum(Calendar.DAY_OF_MONTH) - 1);
    }

    @NotNull
    private static TimeSpan getMonthTimeSpan(Calendar calendar, int i) {
        Date monthStart = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, i);
        Date monthEnd = calendar.getTime();
        return new TimeSpan(monthStart, monthEnd);
    }

}
