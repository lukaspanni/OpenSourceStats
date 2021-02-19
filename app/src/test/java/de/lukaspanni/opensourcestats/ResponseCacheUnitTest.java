package de.lukaspanni.opensourcestats;

import org.junit.Test;

import java.util.Date;

import de.lukaspanni.opensourcestats.mock.FakeResponseData;
import de.lukaspanni.opensourcestats.repository.cache.ResponseCache;
import de.lukaspanni.opensourcestats.data.ResponseData;
import de.lukaspanni.opensourcestats.data.TimeSpan;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

//TODO: Update tests to use other cacheKey instead of TimeSpan
public class ResponseCacheUnitTest {

    @Test
    public void cache_get_returnsCorrectEntry() {
        ResponseCache<TimeSpan, FakeResponseData> testCache = new ResponseCache<>();
        FakeResponseData testResponseDataCorrect = new FakeResponseData(42);
        FakeResponseData testResponseDataWrong = new FakeResponseData(51);
        TimeSpan correctTimeSpan = new TimeSpan(new Date(2020, 11, 9), new Date(2020, 11, 10));
        TimeSpan wrongTimeSpan = new TimeSpan(new Date(2020, 10, 1), new Date(2020, 10, 2));
        testCache.put(correctTimeSpan, testResponseDataCorrect);
        testCache.put(wrongTimeSpan, testResponseDataWrong);

        ResponseData cachedData = testCache.get(correctTimeSpan);
        assertThat(cachedData, is(testResponseDataCorrect));
    }

    @Test
    public void cache_put_replaceCorrect() {
        ResponseCache<TimeSpan, FakeResponseData> testCache = new ResponseCache<>();
        FakeResponseData testResponseDataOld = new FakeResponseData(42);
        FakeResponseData testResponseDataReplace = new FakeResponseData(51);
        TimeSpan dataTimeSpan = new TimeSpan(new Date(2020, 11, 9), new Date(2020, 11, 10));
        testCache.put(dataTimeSpan, testResponseDataOld);
        testCache.put(dataTimeSpan, testResponseDataReplace);

        ResponseData cachedData = testCache.get(dataTimeSpan);
        assertThat(cachedData, is(testResponseDataReplace));
    }

    @Test
    public void cache_HitCount_isCorrect() {
        ResponseCache<TimeSpan, FakeResponseData> testCache = new ResponseCache<>();
        FakeResponseData testResponseData = new FakeResponseData(42);
        TimeSpan dataTimeSpan = new TimeSpan(new Date(2020, 11, 9), new Date(2020, 11, 10));
        testCache.put(dataTimeSpan, testResponseData);

        assertThat(testCache.getHits(), is(0));
        ResponseData cachedData = testCache.get(dataTimeSpan);
        assertThat(cachedData, is(notNullValue()));
        assertThat(testCache.getHits(), is(1));
        cachedData = testCache.get(dataTimeSpan);
        assertThat(cachedData, is(notNullValue()));
        assertThat(testCache.getHits(), is(2));
    }

    @Test
    public void cache_MissCount_isCorrect() {
        ResponseCache<TimeSpan, FakeResponseData> testCache = new ResponseCache<>();
        TimeSpan dataTimeSpan = new TimeSpan(new Date(2020, 11, 9), new Date(2020, 11, 10));

        assertThat(testCache.getMisses(), is(0));
        ResponseData cachedData = testCache.get(dataTimeSpan);
        assertThat(cachedData, is(nullValue()));
        assertThat(testCache.getMisses(), is(1));
        cachedData = testCache.get(dataTimeSpan);
        assertThat(cachedData, is(nullValue()));
        assertThat(testCache.getMisses(), is(2));
    }

    @Test
    public void cache_Clear_Empty(){
        ResponseCache<TimeSpan, FakeResponseData> testCache = new ResponseCache<>();
        FakeResponseData testResponseData = new FakeResponseData(42);
        TimeSpan dataTimeSpan = new TimeSpan(new Date(2021, 2, 18), new Date(2022, 2, 19));
        testCache.put(dataTimeSpan, testResponseData);

        ResponseData cachedData = testCache.get(dataTimeSpan);
        assertThat(cachedData, is(notNullValue()));
        assertThat(cachedData, is(equalTo(testResponseData)));

        testCache.clearCache();

        assertThat(testCache.getHits(), is(0));
        assertThat(testCache.getMisses(), is(0));
        ResponseData noCachedData = testCache.get(dataTimeSpan);
        assertThat(noCachedData, is(nullValue()));
    }


}
