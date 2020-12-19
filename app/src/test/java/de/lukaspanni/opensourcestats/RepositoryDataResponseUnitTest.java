package de.lukaspanni.opensourcestats;

import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import de.lukaspanni.opensourcestats.data.RepositoryDataResponse;
import de.lukaspanni.opensourcestats.mock.RepositoryFake;

import static org.hamcrest.CoreMatchers.is;
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

        assertThat(repoLanguages, is(languages));
    }

}
