package de.lukaspanni.opensourcestats.client;

import android.util.Log;

import com.apollographql.apollo.api.CustomTypeAdapter;
import com.apollographql.apollo.api.CustomTypeValue;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateCustomTypeAdapter implements CustomTypeAdapter<Date> {
    private final DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @NotNull
    @Override
    public CustomTypeValue<?> encode(Date date) {
        return new CustomTypeValue.GraphQLString(format.format(date));
    }

    @Override
    public Date decode(@NotNull CustomTypeValue<?> customTypeValue) {
        try {
            return format.parse((String) customTypeValue.value);
        } catch (ParseException e) {
            Log.e("Conversion Error", e.getLocalizedMessage());
            return null;
        }
    }
}
