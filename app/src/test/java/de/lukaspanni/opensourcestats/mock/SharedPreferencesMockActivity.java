package de.lukaspanni.opensourcestats.mock;

import android.app.Activity;
import android.content.SharedPreferences;

public class SharedPreferencesMockActivity extends Activity {
    private SharedPreferences sharedPreferences;

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public SharedPreferences getSharedPreferences(String name, int mode) {
        return sharedPreferences;
    }
}
