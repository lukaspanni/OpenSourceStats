package de.lukaspanni.opensourcestats;

import org.junit.Test;
import java.util.Map;

import de.lukaspanni.opensourcestats.auth.AuthenticationHandler;
import de.lukaspanni.opensourcestats.data.RepositoryName;
import de.lukaspanni.opensourcestats.data.TimeSpan;
import de.lukaspanni.opensourcestats.data.TimeSpanFactory;
import de.lukaspanni.opensourcestats.mock.GithubOAuthClientTestImplementation;
import de.lukaspanni.opensourcestats.mock.MockAuthenticationHandler;
import de.lukaspanni.opensourcestats.mock.MockClientCallback;
import de.lukaspanni.opensourcestats.mock.MockApolloQueryCallFactory;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class GithubOAuthClientUnitTest {
    //Not really good tests because they doesn't test much, but better than nothing


    @Test
    public void test_loadRepositoryData() {
        //Only checks if RepositoryName is converted correctly to Query
        final String login = "lukaspanni";
        final String repository = "OpenSourceStats";
        RepositoryName testRepository = new RepositoryName(login, repository);
        AuthenticationHandler authHandler = new MockAuthenticationHandler();
        MockClientCallback mockCallback = new MockClientCallback();
        MockApolloQueryCallFactory mockApolloClient = new MockApolloQueryCallFactory(new MockApolloQueryCallFactory.MockApolloQueryCall<RepositoryDataQuery.Data>());
        GithubOAuthClientTestImplementation repositoryDataClient = new GithubOAuthClientTestImplementation(authHandler);
        repositoryDataClient.setMockGraphqlClient(mockApolloClient);

        repositoryDataClient.loadRepositoryData(testRepository, mockCallback);

        Map<String, Object> valueMap = ((RepositoryDataQuery) mockApolloClient.getProvidedQuery()).variables().valueMap();
        assertThat(valueMap.get("repository"), is(equalTo(repository)));
        assertThat(valueMap.get("login"), is(equalTo(login)));
    }

    @Test
    public void test_loadUserContributionsData() {
        //Only checks if TimeSpan is converted correctly to Query
        TimeSpan timeSpan = TimeSpanFactory.getCurrentWeek();

        AuthenticationHandler authHandler = new MockAuthenticationHandler();
        MockClientCallback mockCallback = new MockClientCallback();
        MockApolloQueryCallFactory mockApolloClient = new MockApolloQueryCallFactory(new MockApolloQueryCallFactory.MockApolloQueryCall<UserContributionsQuery.Data>());
        GithubOAuthClientTestImplementation userContributionsClient = new GithubOAuthClientTestImplementation(authHandler);
        userContributionsClient.setMockGraphqlClient(mockApolloClient);

        userContributionsClient.loadUserContributionsData(timeSpan, mockCallback);

        Map<String, Object> valueMap = ((UserContributionsQuery) mockApolloClient.getProvidedQuery()).variables().valueMap();
        assertThat(valueMap.get("startDate"), is(equalTo(timeSpan.getStart())));
        assertThat(valueMap.get("endDate"), is(equalTo(timeSpan.getEnd())));
    }


}
