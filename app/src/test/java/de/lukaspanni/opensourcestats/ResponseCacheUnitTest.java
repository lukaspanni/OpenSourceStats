package de.lukaspanni.opensourcestats;

import org.junit.Test;

import java.util.Date;
import java.util.Objects;

import de.lukaspanni.opensourcestats.client.cache.ResponseCache;
import de.lukaspanni.opensourcestats.data.ResponseData;
import de.lukaspanni.opensourcestats.util.TimeSpan;

import static org.junit.Assert.*;


public class ResponseCacheUnitTest {

    @Test
    public void cache_get_returnsCorrectEntry() {
        ResponseCache testCache = ResponseCache.getInstance();
        FakeResponseData testResponseDataCorrect = new FakeResponseData(42);
        FakeResponseData testResponseDataWrong = new FakeResponseData(51);
        TimeSpan correctTimeSpan = new TimeSpan(new Date(2020, 11, 9), new Date(2020, 11, 10));
        TimeSpan wrongTimeSpan = new TimeSpan(new Date(2020, 10, 1), new Date(2020, 10, 2));
        testCache.put(correctTimeSpan, testResponseDataCorrect);
        testCache.put(wrongTimeSpan, testResponseDataWrong);

        ResponseData cachedData = testCache.get(correctTimeSpan);
        assertEquals(testResponseDataCorrect, cachedData);
    }

    @Test
    public void cache_put_replaceCorrect() {
        ResponseCache testCache = ResponseCache.getInstance();
        FakeResponseData testResponseDataOld = new FakeResponseData(42);
        FakeResponseData testResponseDataReplace = new FakeResponseData(51);
        TimeSpan dataTimeSpan = new TimeSpan(new Date(2020, 11, 9), new Date(2020, 11, 10));
        testCache.put(dataTimeSpan, testResponseDataOld);
        testCache.put(dataTimeSpan, testResponseDataReplace);

        ResponseData cachedData = testCache.get(dataTimeSpan);
        assertEquals(testResponseDataReplace, cachedData);
    }

    @Test
    public void cache_HitCount_isCorrect() {
        ResponseCache testCache = ResponseCache.getInstance();
        FakeResponseData testResponseData = new FakeResponseData(42);
        TimeSpan dataTimeSpan = new TimeSpan(new Date(2020, 11, 9), new Date(2020, 11, 10));
        testCache.put(dataTimeSpan, testResponseData);

        assertEquals(0, testCache.getHits());
        ResponseData cachedData = testCache.get(dataTimeSpan);
        assertNotNull(cachedData);
        assertEquals(1, testCache.getHits());
        cachedData = testCache.get(dataTimeSpan);
        assertNotNull(cachedData);
        assertEquals(2, testCache.getHits());
    }

    @Test
    public void cache_MissCount_isCorrect() {
        ResponseCache testCache = ResponseCache.getInstance();
        TimeSpan dataTimeSpan = new TimeSpan(new Date(2020, 11, 9), new Date(2020, 11, 10));

        assertEquals(0, testCache.getMisses());
        ResponseData cachedData = testCache.get(dataTimeSpan);
        assertNull(cachedData);
        assertEquals(1, testCache.getMisses());
        cachedData = testCache.get(dataTimeSpan);
        assertNull(cachedData);
        assertEquals(2, testCache.getMisses());
    }

    @Test
    public void cache_overMaxAge_miss(){
        ResponseCache testCache = ResponseCache.getInstance(-1);    //negative MaxAge should not be possible -> update cache
        FakeResponseData testResponseData = new FakeResponseData(42);
        TimeSpan dataTimeSpan = new TimeSpan(new Date(2020, 11, 9), new Date(2020, 11, 10));
        testCache.put(dataTimeSpan, testResponseData);

        assertNull(testCache.get(dataTimeSpan));
    }

    private class FakeResponseData extends ResponseData {

        private int data;

        public FakeResponseData(int data) {
            super(QueryType.NONE);
            this.data = data;
        }

        public int getData() {
            return data;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            FakeResponseData that = (FakeResponseData) o;
            return getData() == that.getData();
        }

        @Override
        public int hashCode() {
            return Objects.hash(getData());
        }
    }
}
