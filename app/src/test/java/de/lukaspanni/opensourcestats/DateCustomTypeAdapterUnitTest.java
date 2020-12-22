package de.lukaspanni.opensourcestats;

import com.apollographql.apollo.api.CustomTypeValue;

import org.junit.Test;

import java.util.Date;

import de.lukaspanni.opensourcestats.client.DateCustomTypeAdapter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DateCustomTypeAdapterUnitTest {

    @Test
    public void test_encode() {
        //=> 2020-12-20T15:20:12
        Date testDate = new Date(120, 11, 20, 15, 20, 12);
        String expected = "2020-12-20T15:20:12";

        DateCustomTypeAdapter testAdapter = new DateCustomTypeAdapter();
        CustomTypeValue.GraphQLString actual = (CustomTypeValue.GraphQLString) testAdapter.encode(testDate);

        assertThat(actual.value, is(equalTo(expected)));
    }

    @Test
    public void test_decode(){
        String encoded = "2020-12-20T15:20:12";
        //=> 2020-12-20T15:20:12
        Date expected = new Date(120, 11, 20, 15, 20, 12);

        DateCustomTypeAdapter testAdapter = new DateCustomTypeAdapter();
        Date actual = testAdapter.decode(CustomTypeValue.fromRawValue(encoded));

        assertThat(actual, is(equalTo(expected)));
    }

}