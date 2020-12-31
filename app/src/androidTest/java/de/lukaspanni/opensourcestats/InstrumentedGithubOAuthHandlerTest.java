package de.lukaspanni.opensourcestats;


import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.MediumTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import de.lukaspanni.opensourcestats.auth.AuthenticationHandler;

import static org.junit.Assert.assertTrue;

@MediumTest
@RunWith(AndroidJUnit4.class)
public class InstrumentedGithubOAuthHandlerTest {

    @Test
    public void test_auth_handler_authenticate() {
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            scenario.onActivity(activity -> {
                AuthenticationHandler handler = activity.getAuthHandler();
                handler.authenticate();
                assertTrue(handler.checkAuth());
            });
        }
    }
}