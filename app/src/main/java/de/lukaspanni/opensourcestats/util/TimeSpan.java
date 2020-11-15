package de.lukaspanni.opensourcestats.util;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;

import de.lukaspanni.opensourcestats.repository.cache.CacheKey;


/**
 * TimeSpan ValueObject, represents a time span with specified start and end date
 */
public final class TimeSpan implements Parcelable, CacheKey {
    private final Date start;
    private final Date end;

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
    }


    protected TimeSpan(Parcel in) {
        long[] data = new long[2];
        in.readLongArray(data);
        this.start = new Date(data[0]);
        this.end = new Date(data[1]);
    }

    public static final Creator<TimeSpan> CREATOR = new Creator<TimeSpan>() {
        @Override
        public TimeSpan createFromParcel(Parcel in) {
            return new TimeSpan(in);
        }

        @Override
        public TimeSpan[] newArray(int size) {
            return new TimeSpan[size];
        }
    };

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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLongArray(new long[] {this.start.getTime(), this.end.getTime()});
    }

    @Override
    public long getKey() {
        return hashCode();
    }
}
