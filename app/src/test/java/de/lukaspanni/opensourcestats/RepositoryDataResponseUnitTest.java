package de.lukaspanni.opensourcestats;

import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import de.lukaspanni.opensourcestats.data.RepositoryDataResponse;
import de.lukaspanni.opensourcestats.mock.RepositoryFake;

import static org.hamcrest.CoreMatchers.equalTo;
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

        assertThat(testDataResponse.getLanguages(), is(languages));
    }

    @Test
    public void constructor_others_correct(){
        String primaryLanguage = "Java";
        boolean isPrivate = true;
        String description = "Sample Description Test";
        Date createdAt = new Date(192, 10,11);
        RepositoryFake repositoryFake = RepositoryFake.create(createdAt, primaryLanguage, isPrivate, description, new HashSet<>());

        RepositoryDataResponse testDataResponse = new RepositoryDataResponse(repositoryFake);

        assertThat(testDataResponse.getCreatedAt(), is(equalTo(createdAt)));
        assertThat(testDataResponse.isPrivate(), is(equalTo(isPrivate)));
        assertThat(testDataResponse.getPrimaryLanguage(), is(equalTo(primaryLanguage)));
        assertThat(testDataResponse.getDescription(), is(equalTo(description)));
    }

}
