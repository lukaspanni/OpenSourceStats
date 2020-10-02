package com.example.opensoucestats.client;

import java.util.Date;

class TimeSpan {
    public Date start;
    public Date end;

    public TimeSpan(Date start, Date end) {
        this.start = start;
        this.end = end;
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
}
