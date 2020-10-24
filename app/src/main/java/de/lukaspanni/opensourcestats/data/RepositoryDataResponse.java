package de.lukaspanni.opensourcestats.data;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import de.lukaspanni.opensourcestats.RepositoryDataQuery;

public class RepositoryDataResponse extends ResponseData {

    private Date createdAt;
    private String description;
    private String primaryLanguage;
    private Set<String> languages;
    private boolean isPrivate;

    public RepositoryDataResponse(RepositoryDataQuery.Repository repository) {
        super(QueryType.REPOSITORY_DATA_QUERY);
        this.createdAt = repository.createdAt();
        if (repository.primaryLanguage() != null) {
            this.primaryLanguage = repository.primaryLanguage().name();
        }
        this.isPrivate = repository.isPrivate();
        this.description = repository.description();
        if (repository.languages() != null) {
            this.languages = repository.languages().nodes().stream().map(RepositoryDataQuery.Node::name).collect(Collectors.toSet());
        }
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getPrimaryLanguage() {
        return primaryLanguage;
    }

    public Set<String> getLanguages() {
        return languages;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public String getDescription() {
        return description;
    }
}
