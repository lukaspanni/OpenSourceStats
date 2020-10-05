package de.lukaspanni.opensourcestats;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.lukaspanni.opensourcestats.R;

import de.lukaspanni.opensourcestats.auth.AuthActivity;
import de.lukaspanni.opensourcestats.auth.AuthHandler;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    private AuthHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_details, R.id.navigation_dashboard, R.id.navigation_progress)
                .build();
        getSupportActionBar().hide();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        //TODO: start with AuthActivity -> less activity switches
        handler = AuthHandler.getInstance(this);
        if (!handler.checkAuth() && getIntent().getAction() != null) {
            Intent authIntent = new Intent(this, AuthActivity.class);
            startActivity(authIntent);
        }

    }

}