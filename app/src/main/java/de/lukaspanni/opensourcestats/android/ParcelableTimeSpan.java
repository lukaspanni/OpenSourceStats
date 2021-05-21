package de.lukaspanni.opensourcestats.android;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import de.lukaspanni.opensourcestats.data.TimeSpan;

public class ParcelableTimeSpan extends TimeSpan implements Parcelable {

    public ParcelableTimeSpan(Date start, Date end) {
        super(start, end);
    }

    public ParcelableTimeSpan(TimeSpan timeSpan){
        super(timeSpan.getStart(), timeSpan.getEnd());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLongArray(new long[]{this.start.getTime(), this.end.getTime()});
    }

    public static final Creator<TimeSpan> CREATOR = new Creator<TimeSpan>() {
        @Override
        public TimeSpan createFromParcel(Parcel in) {
            long[] data = new long[2];
            in.readLongArray(data);
            return new TimeSpan( new Date(data[0]),  new Date(data[1]));
        }

        @Override
        public TimeSpan[] newArray(int size) {
            return new TimeSpan[size];
        }
    };
}
