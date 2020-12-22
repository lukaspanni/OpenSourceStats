package de.lukaspanni.opensourcestats.mock;

import android.app.Activity;
import android.content.SharedPreferences;

import de.lukaspanni.opensourcestats.auth.AuthHandlerActivity;

import static android.content.Context.MODE_PRIVATE;

public class MockAuthHandlerActivity implements AuthHandlerActivity {

    private Activity mockActivity;
    private SharedPreferences sharedPreferences;

    public MockAuthHandlerActivity() {
        this(null, null);
    }

    public MockAuthHandlerActivity(SharedPreferences sharedPreferences) {
        this(sharedPreferences, null);
    }

    public MockAuthHandlerActivity(SharedPreferences sharedPreferences, Activity mockActivity) {
        this.sharedPreferences = sharedPreferences;
        this.mockActivity = mockActivity;
    }

    @Override
    public String getClientId() {
        //Random Client-ID
        return "678ef41ce100a242b1ff";
    }

    @Override
    public Activity getActivity() {
        return mockActivity;
    }

    @Override
    public SharedPreferences getAuthPreferences() {
        return sharedPreferences;
    }
}
