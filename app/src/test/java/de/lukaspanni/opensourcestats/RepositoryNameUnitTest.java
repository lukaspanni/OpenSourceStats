package de.lukaspanni.opensourcestats;

import org.junit.Test;

import de.lukaspanni.opensourcestats.util.RepositoryName;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class RepositoryNameUnitTest {

    @Test
    public void test_constructor_name_split(){
        String owner = "lukaspanni";
        String repository = "ImageCopy";
        String repositoryWithOwner = "lukaspanni/ImageCopy";
        RepositoryName testObject = new RepositoryName(repositoryWithOwner);
        assertThat(testObject.getOwner(), is(equalTo(owner)));
        assertThat(testObject.getName(), is(equalTo(repository)));
    }

    @Test
    public void test_equals(){
        String owner1 = "lukaspanni";
        String repository1 = "ImageCopy";
        String owner2 = "lukaspanni";
        String repository2 = "ImageCopy";
        RepositoryName testObject1 = new RepositoryName(owner1, repository1);
        RepositoryName testObject2 = new RepositoryName(owner2, repository2);
        assertThat(testObject2.equals(testObject1), is(true));
    }

}
