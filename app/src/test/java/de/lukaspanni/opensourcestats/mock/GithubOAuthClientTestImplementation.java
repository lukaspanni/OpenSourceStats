package de.lukaspanni.opensourcestats.mock;

import com.apollographql.apollo.ApolloQueryCall;

import de.lukaspanni.opensourcestats.auth.AuthenticationHandler;
import de.lukaspanni.opensourcestats.client.GithubOAuthClient;
import de.lukaspanni.opensourcestats.mock.MockApolloQueryCallFactory;

public class GithubOAuthClientTestImplementation extends GithubOAuthClient {
    private MockApolloQueryCallFactory mockClient;

    public GithubOAuthClientTestImplementation(AuthenticationHandler handler) {
        super(handler);
    }

    public void setMockGraphqlClient(MockApolloQueryCallFactory client){
        mockClient = client;
    }

    public MockApolloQueryCallFactory getMockClient() {
        return mockClient;
    }

    @Override
    protected ApolloQueryCall.Factory getGraphqlClient(String accessToken) {
        mockClient.setProvidedAccessToken(accessToken);
       return mockClient;
    }
}
