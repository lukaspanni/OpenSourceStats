package de.lukaspanni.opensourcestats.data;

import java.util.Objects;

import de.lukaspanni.opensourcestats.repository.cache.CacheKey;

public final class RepositoryName implements CacheKey {

    private final String owner;
    private final String name;

    public RepositoryName(String owner, String name) {
        this.owner = owner;
        this.name = name;
    }

    public RepositoryName(String nameWithOwner){
        String[] split = nameWithOwner.split("/");
        this.owner = split[0];
        this.name = split[1];
    }

    public String getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RepositoryName that = (RepositoryName) o;
        return Objects.equals(owner, that.owner) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner, name);
    }

    @Override
    public long getKey() {
        return hashCode();
    }
}
