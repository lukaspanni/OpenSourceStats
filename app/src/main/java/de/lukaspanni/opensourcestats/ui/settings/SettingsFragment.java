package de.lukaspanni.opensourcestats.ui.settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.lukaspanni.opensourcestats.R;

import de.lukaspanni.opensourcestats.ApplicationConfig;
import de.lukaspanni.opensourcestats.OpenSourceStatsApplication;
import de.lukaspanni.opensourcestats.auth.AuthActivity;


public class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceClickListener {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.perferences);

        findPreference("auth_revoke_authentication").setOnPreferenceClickListener(this);
        findPreference("auth_restart_authentication").setOnPreferenceClickListener(this);
        findPreference("auth_check_authentication").setOnPreferenceClickListener(this);

    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (preference.getKey().equals("auth_check_authentication")) {
            Toast checkToast;
            //TODO: Implement Auth-Check
            if (true)
                checkToast = Toast.makeText(getContext(), R.string.check_auth_true, Toast.LENGTH_SHORT);
            else
                checkToast = Toast.makeText(getContext(), R.string.check_auth_false, Toast.LENGTH_SHORT);
            checkToast.show();
        } else if (preference.getKey().equals("auth_restart_authentication")) {
            Intent authIntent = new Intent(getContext(), AuthActivity.class);
            startActivity(authIntent);
        } else if (preference.getKey().equals("auth_revoke_authentication")) {
            String deletedState = null;
            Activity parentActivity = getActivity();


            parentActivity = null;
            if (parentActivity != null) {
                //Delete save authentication
                SharedPreferences authPreferences = parentActivity.getSharedPreferences("auth", Context.MODE_PRIVATE);
                authPreferences.edit().putString(ApplicationConfig.getAuthStateSharedPreferencesKey(), deletedState).apply();
                //Delete all data from caches
                OpenSourceStatsApplication app = (OpenSourceStatsApplication) parentActivity.getApplication();
                app.clearRepositoryCaches();
            }else return false;
        }
        return true;
    }
}
