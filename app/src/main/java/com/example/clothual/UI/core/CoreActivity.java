package com.example.clothual.UI.core;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.clothual.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class CoreActivity extends AppCompatActivity{


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.core_layout);

        ImageView clothual = findViewById(R.id.clothual);

        int nightModeFlags =
                getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                clothual.setImageResource(R.drawable.logo_white_on_appbar);
                break;

            case Configuration.UI_MODE_NIGHT_NO:
                clothual.setImageResource(R.drawable.logo_black_on_white);
                break;
        }



        
        Toolbar toolbar = findViewById(R.id.top_appbar);
        setSupportActionBar(toolbar);


        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().
                findFragmentById(R.id.nav_host_fragment);

        NavController navController = navHostFragment.getNavController();
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeFragment, R.id.calendarFragment,
                R.id.favoriteFragment, R.id.photoFragment, R.id.personalFragment).build();



        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // For the Toolbar
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // For the BottomNavigationView
        NavigationUI.setupWithNavController(bottomNav, navController);
    }
}
