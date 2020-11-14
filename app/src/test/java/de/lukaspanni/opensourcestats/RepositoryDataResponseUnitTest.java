package de.lukaspanni.opensourcestats;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.lukaspanni.opensourcestats.data.RepositoryDataResponse;

import static org.junit.Assert.*;

public class RepositoryDataResponseUnitTest {

    @Test
    public void constructor_otherLanguagesSet_correct() {
        Set<String> languages = new HashSet<>();
        languages.add("Java");
        languages.add("Kotlin");
        languages.add("C#");
        RepositoryFake repositoryFake = RepositoryFake.create(new Date(), "Java", false, "Sample Description", languages);
        RepositoryDataResponse testDataResponse = new RepositoryDataResponse(repositoryFake);
        Set<String> repoLanguages = testDataResponse.getLanguages();
        assertEquals(languages, repoLanguages);
    }

    private static class RepositoryFake extends RepositoryDataQuery.Repository {

        public static RepositoryFake create(Date createdAt, String primaryLanguageName, boolean isPrivate, String description, Set<String> languageNames) {
            List<RepositoryDataQuery.Node> nodes = new ArrayList<>();
            for (String language: languageNames) {
                nodes.add(new RepositoryDataQuery.Node("language", language));
            }
            RepositoryDataQuery.Languages languages = new RepositoryDataQuery.Languages("Languages", nodes);
            RepositoryDataQuery.PrimaryLanguage primaryLanguage = new RepositoryDataQuery.PrimaryLanguage("primaryLanguage", primaryLanguageName);
            return new RepositoryFake(createdAt, primaryLanguage, isPrivate, description, languages);
        }

        public RepositoryFake(@NotNull Date createdAt, @Nullable RepositoryDataQuery.PrimaryLanguage primaryLanguage, boolean isPrivate, @Nullable String description, @Nullable RepositoryDataQuery.Languages languages) {
            super("Repository", createdAt, primaryLanguage, isPrivate, description, languages);

        }
    }

}
