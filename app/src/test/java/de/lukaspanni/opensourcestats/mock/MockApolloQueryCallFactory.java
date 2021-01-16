package de.lukaspanni.opensourcestats.mock;

import com.apollographql.apollo.ApolloQueryCall;
import com.apollographql.apollo.ApolloQueryWatcher;
import com.apollographql.apollo.api.Operation;
import com.apollographql.apollo.api.Query;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.api.cache.http.HttpCachePolicy;
import com.apollographql.apollo.cache.CacheHeaders;
import com.apollographql.apollo.fetcher.ResponseFetcher;
import com.apollographql.apollo.request.RequestHeaders;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import de.lukaspanni.opensourcestats.RepositoryDataQuery;


public class MockApolloQueryCallFactory implements ApolloQueryCall.Factory {

    private String providedAccessToken;
    private Query providedQuery;

    public Query getProvidedQuery() {
        return providedQuery;
    }

    public String getProvidedAccessToken() {
        return providedAccessToken;
    }

    public void setProvidedAccessToken(String providedAccessToken) {
        this.providedAccessToken = providedAccessToken;
    }

    @Override
    public <D extends Operation.Data, T, V extends Operation.Variables> ApolloQueryCall<T> query(@NotNull Query<D, T, V> query) {
        this.providedQuery = query;
        return (ApolloQueryCall<T>) new MockApolloQueryCall<RepositoryDataQuery.Data>();
    }

    private static class MockApolloQueryCall<T> implements ApolloQueryCall<T> {

        @Override
        public void enqueue(@Nullable Callback<T> callback) {
        }

        @NotNull
        @Override
        public ApolloQueryWatcher<T> watcher() {
            return null;
        }

        @NotNull
        @Override
        public ApolloQueryCall<T> httpCachePolicy(@NotNull HttpCachePolicy.Policy httpCachePolicy) {
            return null;
        }

        @NotNull
        @Override
        public ApolloQueryCall<T> cacheHeaders(@NotNull CacheHeaders cacheHeaders) {
            return null;
        }

        @NotNull
        @Override
        public ApolloQueryCall<T> responseFetcher(@NotNull ResponseFetcher fetcher) {
            return null;
        }

        @NotNull
        @Override
        public ApolloQueryCall<T> requestHeaders(@NotNull RequestHeaders requestHeaders) {
            return null;
        }

        @NotNull
        @Override
        public ApolloQueryCall<T> clone() {
            return null;
        }

        @NotNull
        @Override
        public Builder<T> toBuilder() {
            return null;
        }


        @NotNull
        @Override
        public Operation operation() {
            return null;
        }

        @Override
        public void cancel() {

        }

        @Override
        public boolean isCanceled() {
            return false;
        }
    }
}
