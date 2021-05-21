package de.lukaspanni.opensourcestats.data;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import de.lukaspanni.opensourcestats.repository.cache.CacheKey;


/**
 * TimeSpan ValueObject, represents a time span with specified start and end date
 */
public class TimeSpan implements CacheKey {
    protected final Date start;
    protected final Date end;

    /**
     * Set Hour, Minute, Second and Millisecond to zero for better comparability
     * @param date input date
     * @return date with h,m,s,ms set to zero
     */
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
        if(!isZeroDateSmaller(start, end)){
            throw new IllegalArgumentException("Start Date has to be smaller than end Date");
        }
    }

    private boolean isZeroDateSmaller(Date start, Date end) {
        return start.compareTo(end) <= 0;
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

    @Override
    public long getKey() {
        return hashCode();
    }

    public String toFormattedString(){
        return dateFormattedString(getStart()) +  " - " + dateFormattedString(getEnd()) ;
    }

    public static String dateFormattedString(Date date){
        DateFormat formatter = DateFormat.getDateInstance();
        return formatter.format(date);
    }
}
