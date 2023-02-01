package com.example.clothual.UI.core;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.clothual.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CoreActivity extends AppCompatActivity {


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.core_layout);


        Toolbar toolbar = findViewById(R.id.top_appbar);
        setSupportActionBar(toolbar);

       /* CollapsingToolbarLayout toolBarLayout = findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        */

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().
                findFragmentById(R.id.nav_host_fragment);

        NavController navController = navHostFragment.getNavController();
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);


        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeFragment, R.id.calendarFragment,
                R.id.matchingFragment, R.id.photoFragment, R.id.personalFragment).build();



        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // For the Toolbar
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);



        // For the BottomNavigationView
        NavigationUI.setupWithNavController(bottomNav, navController);

    }
/*
    @Override
    public void onBackPressed() {
        NavBackStackEntry navBackStackEntry = Navigation.findNavController(this, R.id.nav_core).getPreviousBackStackEntry();
        if(navBackStackEntry != null && (//navBackStackEntry.getDestination().getId() != R.id.homeFragment ||
                navBackStackEntry.getDestination().getId() == com.google.android.gms.auth.api.R.id.all
        )) {
            finish();
        }else{
            finish();
        }
    }

 */
}
