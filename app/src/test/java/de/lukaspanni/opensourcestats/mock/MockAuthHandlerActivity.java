package de.lukaspanni.opensourcestats.mock;

import android.app.Activity;

import de.lukaspanni.opensourcestats.auth.AuthHandlerActivity;

public class MockAuthHandlerActivity implements AuthHandlerActivity {

    private Activity mockActivity;

    public MockAuthHandlerActivity() {
        this(null);
    }

    public MockAuthHandlerActivity(Activity mockActivity) {
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
}
