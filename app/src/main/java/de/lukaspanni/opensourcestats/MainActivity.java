package de.lukaspanni.opensourcestats;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lukaspanni.opensourcestats.R;

import de.lukaspanni.opensourcestats.auth.AuthActivity;
import de.lukaspanni.opensourcestats.auth.AuthHandlerActivity;
import de.lukaspanni.opensourcestats.auth.AuthenticationHandler;
import de.lukaspanni.opensourcestats.client.GithubOAuthClient;
import de.lukaspanni.opensourcestats.repository.RepositoryDataRepository;
import de.lukaspanni.opensourcestats.repository.UserContributionsRepository;
import de.lukaspanni.opensourcestats.settings.SettingsActivity;


public class MainActivity extends AppCompatActivity implements AuthHandlerActivity {

    private OpenSourceStatsApplication app;

    public AuthenticationHandler getAuthHandler() {
        return app.getAuthHandler(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (OpenSourceStatsApplication) getApplication();
        GithubOAuthClient client = new GithubOAuthClient(getAuthHandler());
        // Add Repositories
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
        if (!getAuthHandler().checkAuth() && getIntent().getAction() != null) {
            Intent authIntent = new Intent(this, AuthActivity.class);
            startActivity(authIntent);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_settings){
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
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