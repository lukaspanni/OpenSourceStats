package de.lukaspanni.opensourcestats;

import org.junit.Test;

import java.util.Calendar;
import java.util.HashMap;

import de.lukaspanni.opensourcestats.client.ClientDataCallback;
import de.lukaspanni.opensourcestats.client.ClientDataCallbackDecorator;
import de.lukaspanni.opensourcestats.data.ContributionCount;
import de.lukaspanni.opensourcestats.data.UserContributionsResponse;
import de.lukaspanni.opensourcestats.mock.ContributionsViewerFake;
import de.lukaspanni.opensourcestats.mock.FakeUserContributionsClient;
import de.lukaspanni.opensourcestats.mock.MockClientCallback;
import de.lukaspanni.opensourcestats.repository.UserContributionsRepository;
import de.lukaspanni.opensourcestats.repository.cache.ResponseCache;
import de.lukaspanni.opensourcestats.util.TimeSpan;
import de.lukaspanni.opensourcestats.util.TimeSpanFactory;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UserContributionsRepositoryUnitTest {

    @Test
    public void test_repository_summary_force_reload_client_not_cache() {
        TimeSpan timeSpan = TimeSpanFactory.getCurrentWeek();
        FakeUserContributionsClient client = new FakeUserContributionsClient();
        ResponseCache<TimeSpan, UserContributionsResponse> cache = new ResponseCache<>();
        UserContributionsRepository testObject = new UserContributionsRepository(cache, client);

        testObject.userContributionsTimeSpan(timeSpan, response -> {
        }, true);

        assertThat(client.isCalled(), is(true));
        //if hits or misses > 0 repository has tried to retrieve from cache
        assertThat(cache.getMisses(), is(equalTo(0)));
        assertThat(cache.getHits(), is(equalTo(0)));
    }

    @Test
    public void test_repository_summary_force_reload_decorated_callback() {
        TimeSpan timeSpan = TimeSpanFactory.getCurrentWeek();
        FakeUserContributionsClient client = new FakeUserContributionsClient();
        MockClientCallback callback = new MockClientCallback();
        ResponseCache<TimeSpan, UserContributionsResponse> cache = new ResponseCache<>();
        UserContributionsRepository testObject = new UserContributionsRepository(cache, client);
        ContributionsViewerFake testData = ContributionsViewerFake.create("test", "test", new ContributionCount(10, 5, 2,3), new HashMap());
        UserContributionsResponse fakeResponse = new UserContributionsResponse(testData);

        testObject.userContributionsTimeSpan(timeSpan, callback, true);
        ClientDataCallback decoratedCallback = client.getCalledCallback();
        decoratedCallback.callback(fakeResponse);

        //callback should be decorated with additional callback
        assertThat(client.getCalledCallback(), instanceOf(ClientDataCallbackDecorator.class));
        //original callback should be called
        assertThat(callback.isCallbackCalled(), is(true));
        //cache should contain fakeResponse
        assertThat(cache.get(timeSpan), is(equalTo(fakeResponse)));
    }

    @Test
    public void test_repository_summary_load_from_cache() {
        TimeSpan timeSpan = TimeSpanFactory.getCurrentWeek();
        FakeUserContributionsClient client = new FakeUserContributionsClient();
        MockClientCallback callback = new MockClientCallback();
        ResponseCache<TimeSpan, UserContributionsResponse> cache = new ResponseCache<>();
        UserContributionsRepository testObject = new UserContributionsRepository(cache, client);
        ContributionsViewerFake testData = ContributionsViewerFake.create("test", "test", new ContributionCount(10, 5, 2,3), new HashMap());
        UserContributionsResponse fakeResponse = new UserContributionsResponse(testData);

        //put in cache
        testObject.userContributionsTimeSpan(timeSpan, response -> {
        }, true);
        client.getCalledCallback().callback(fakeResponse);
        //get from cache
        testObject.userContributionsTimeSpan(timeSpan, callback, false);

        assertThat(cache.getMisses(), is(equalTo(0)));
        assertThat(cache.getHits(), is(equalTo(1)));
        assertThat(callback.isCallbackCalled(), is(true));
        assertThat(callback.getCallResponseData(), is(equalTo(fakeResponse)));
    }

}
