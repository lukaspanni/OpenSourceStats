package de.lukaspanni.opensourcestats.auth;


import android.app.Activity;
import android.content.SharedPreferences;

public interface AuthHandlerActivity {

    String getClientId();

    Activity getActivity();

    SharedPreferences getAuthPreferences();

}
