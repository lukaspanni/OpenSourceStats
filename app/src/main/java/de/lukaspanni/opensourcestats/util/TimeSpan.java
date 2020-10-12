package de.lukaspanni.opensourcestats.util;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import static de.lukaspanni.opensourcestats.util.DateUtility.zeroDate;

public class TimeSpan implements Parcelable {
    public Date start;
    public Date end;


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


}
