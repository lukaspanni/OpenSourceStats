package de.lukaspanni.opensourcestats;

import org.junit.Test;

import java.util.Date;
import java.util.HashSet;

import de.lukaspanni.opensourcestats.client.ClientDataCallbackDecorator;
import de.lukaspanni.opensourcestats.data.RepositoryDataResponse;
import de.lukaspanni.opensourcestats.data.ResponseData;
import de.lukaspanni.opensourcestats.mock.MockClientCallback;
import de.lukaspanni.opensourcestats.mock.RepositoryFake;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class ClientDataCallbackDecoratorUnitTest {

    @Test
    public void execute_original_and_additional_callback(){
        MockClientCallback originalCallback = new MockClientCallback();
        MockClientCallback additionalCallback = new MockClientCallback();
        RepositoryFake repositoryFake = RepositoryFake.create(new Date(), "Java", false, "Sample Description", new HashSet<>());
        ResponseData testDataResponse = new RepositoryDataResponse(repositoryFake);
        ClientDataCallbackDecorator decoratedCallback = new ClientDataCallbackDecorator(originalCallback, additionalCallback);

        decoratedCallback.callback(testDataResponse);

        assertTrue(originalCallback.isCallbackCalled());
        assertTrue(additionalCallback.isCallbackCalled());
        assertThat(originalCallback.getCallResponseData(), is(equalTo(testDataResponse)));
        assertThat(additionalCallback.getCallResponseData(), is(equalTo(testDataResponse)));
    }

}