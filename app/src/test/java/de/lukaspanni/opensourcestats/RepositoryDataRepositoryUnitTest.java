package de.lukaspanni.opensourcestats;

import org.junit.Test;

import java.util.Date;
import java.util.HashSet;

import de.lukaspanni.opensourcestats.client.ClientDataCallback;
import de.lukaspanni.opensourcestats.client.ClientDataCallbackDecorator;
import de.lukaspanni.opensourcestats.data.RepositoryDataResponse;
import de.lukaspanni.opensourcestats.mock.FakeRepositoryDataClient;
import de.lukaspanni.opensourcestats.mock.MockClientCallback;
import de.lukaspanni.opensourcestats.mock.RepositoryFake;
import de.lukaspanni.opensourcestats.repository.RepositoryDataRepository;
import de.lukaspanni.opensourcestats.repository.cache.ResponseCache;
import de.lukaspanni.opensourcestats.util.RepositoryName;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class RepositoryDataRepositoryUnitTest {

    @Test
    public void test_repository_summary_force_reload_client_not_cache() {
        RepositoryName repositoryName = new RepositoryName("lukaspanni", "ImageCopy");
        FakeRepositoryDataClient client = new FakeRepositoryDataClient();
        ResponseCache<RepositoryName, RepositoryDataResponse> cache = new ResponseCache<>();
        RepositoryDataRepository testObject = new RepositoryDataRepository(cache, client);

        testObject.loadRepositoryData(repositoryName, response -> {
        }, true);

        assertThat(client.isCalled(), is(true));
        //if hits or misses > 0 repository has tried to retrieve from cache
        assertThat(cache.getMisses(), is(equalTo(0)));
        assertThat(cache.getHits(), is(equalTo(0)));
    }

    @Test
    public void test_repository_summary_force_reload_decorated_callback() {
        RepositoryName repositoryName = new RepositoryName("lukaspanni", "ImageCopy");
        FakeRepositoryDataClient client = new FakeRepositoryDataClient();
        MockClientCallback callback = new MockClientCallback();
        ResponseCache<RepositoryName, RepositoryDataResponse> cache = new ResponseCache<>();
        RepositoryDataRepository testObject = new RepositoryDataRepository(cache, client);
        RepositoryFake repositoryFake = RepositoryFake.create(new Date(), "Java", false, "Sample Description", new HashSet<>());
        RepositoryDataResponse fakeResponse = new RepositoryDataResponse(repositoryFake);

        testObject.loadRepositoryData(repositoryName, callback, true);
        ClientDataCallback decoratedCallback = client.getCalledCallback();
        decoratedCallback.callback(fakeResponse);

        //callback should be decorated with additional callback
        assertThat(client.getCalledCallback(), instanceOf(ClientDataCallbackDecorator.class));
        //original callback should be called
        assertThat(callback.isCallbackCalled(), is(true));
        //cache should contain fakeResponse
        assertThat(cache.get(repositoryName), is(equalTo(fakeResponse)));
    }

    @Test
    public void test_repository_summary_load_from_cache() {
        RepositoryName repositoryName = new RepositoryName("lukaspanni", "ImageCopy");
        FakeRepositoryDataClient client = new FakeRepositoryDataClient();
        MockClientCallback callback = new MockClientCallback();
        ResponseCache<RepositoryName, RepositoryDataResponse> cache = new ResponseCache<>();
        RepositoryDataRepository testObject = new RepositoryDataRepository(cache, client);
        RepositoryFake repositoryFake = RepositoryFake.create(new Date(), "Java", false, "Sample Description", new HashSet<>());
        RepositoryDataResponse fakeResponse = new RepositoryDataResponse(repositoryFake);


        //put in cache
        testObject.loadRepositoryData(repositoryName, response -> {
        }, true);
        client.getCalledCallback().callback(fakeResponse);
        //get from cache
        testObject.loadRepositoryData(repositoryName, callback, false);

        assertThat(cache.getMisses(), is(equalTo(0)));
        assertThat(cache.getHits(), is(equalTo(1)));
        assertThat(callback.isCallbackCalled(), is(true));
        assertThat(callback.getCallResponseData(), is(equalTo(fakeResponse)));
    }


}
