package de.lukaspanni.opensourcestats;


import androidx.test.core.app.ActivityScenario;
import androidx.test.filters.MediumTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import de.lukaspanni.opensourcestats.auth.AuthHandler;

import static org.junit.Assert.*;

@MediumTest
@RunWith(AndroidJUnit4.class)
public class InstrumentedAuthHandlerTest {

    @Test
    public void test_auth_handler_authenticate() {
        try(ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)){
            scenario.onActivity(activity -> {
                AuthHandler handler = activity.getAuthHandler();
                handler.authenticate();
                assertNotNull(handler.getAuthState());
                assertNotNull(handler.getAuthService());
            });
        }
    }
}