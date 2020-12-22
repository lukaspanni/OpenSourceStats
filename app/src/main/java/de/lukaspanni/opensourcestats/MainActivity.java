package de.lukaspanni.opensourcestats;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.lukaspanni.opensourcestats.R;

import de.lukaspanni.opensourcestats.auth.AuthActivity;
import de.lukaspanni.opensourcestats.auth.AuthHandler;
import de.lukaspanni.opensourcestats.auth.AuthHandlerActivity;
import de.lukaspanni.opensourcestats.client.GHClient;
import de.lukaspanni.opensourcestats.repository.RepositoryDataRepository;
import de.lukaspanni.opensourcestats.repository.UserContributionsRepository;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity implements AuthHandlerActivity {

    private AuthHandler handler;

    public AuthHandler getAuthHandler(){
        if(handler == null) {
            handler = AuthHandler.getInstance(this);
        }
        return handler;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add Repositories
        OpenSourceStatsApplication app = (OpenSourceStatsApplication) getApplication();
        GHClient client = new GHClient(getAuthHandler());
        app.setUserContributionsRepository(new UserContributionsRepository(client));
        app.setRepositoryDataRepository(new RepositoryDataRepository(client));

        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_details, R.id.navigation_dashboard, R.id.navigation_progress)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        handler = AuthHandler.getInstance(this);
        if (!handler.checkAuth() && getIntent().getAction() != null) {
            Intent authIntent = new Intent(this, AuthActivity.class);
            startActivity(authIntent);
        }

    }

    @Override
    public String getClientId() {
        return getString(R.string.client_id);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public SharedPreferences getAuthPreferences() {
        //TODO: "auth" -> Constant
        return getSharedPreferences("auth", MODE_PRIVATE);
    }
}