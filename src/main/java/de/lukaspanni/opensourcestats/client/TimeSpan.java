package de.lukaspanni.opensourcestats.client;

import java.util.Calendar;
import java.util.Date;

public class TimeSpan {
    public Date start;
    public Date end;

    private Date zeroDate(Date date){
        Calendar calender = Calendar.getInstance();
        calender.setTime(date);
        calender.set(Calendar.HOUR_OF_DAY, 0);
        calender.set(Calendar.MINUTE, 0);
        calender.set(Calendar.SECOND, 0);
        calender.set(Calendar.MILLISECOND, 0);

        return calender.getTime();
    }

    public TimeSpan(Date start, Date end) {
        this.start = zeroDate(start);
        this.end = zeroDate(end);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeSpan timeSpan = (TimeSpan) o;
        return timeSpan.start.compareTo(start) == 0 && timeSpan.end.compareTo(end) == 0;
    }

    @Override
    public int hashCode() {
        return start.hashCode() + end.hashCode();
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }
}
