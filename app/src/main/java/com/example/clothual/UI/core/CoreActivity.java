package com.example.clothual.UI.core;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.clothual.R;
import com.example.clothual.UI.core.Calendar.CalendarFragment;
import com.example.clothual.UI.core.Home.HomeFragment;
import com.example.clothual.UI.core.Map.MapFragment;
import com.example.clothual.UI.core.Personal.PersonalFragment;
import com.example.clothual.UI.core.Photo.PhotoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class CoreActivity extends AppCompatActivity{

    private static final int REQUEST_CODE = 101;
    private HomeFragment homeFragment = new HomeFragment();
    private MapFragment mapFragment = new MapFragment();
    private PhotoFragment photoFragment = new PhotoFragment();
    private CalendarFragment calendarFragment = new CalendarFragment();
    private PersonalFragment personalFragment = new PersonalFragment();

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
                R.id.mapFragment, R.id.photoFragment, R.id.personalFragment).build();



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

        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch(item.getItemId()){
                    case R.id.homeFragment:
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,homeFragment).commit();
                        break;
                    case R.id.mapFragment:
                        if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                                ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(CoreActivity.this, new String[]
                                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
                        }
                        else{
                            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,mapFragment).commit();
                        }
                        break;
                    case R.id.photoFragment:
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,photoFragment).commit();
                        break;
                    case R.id.calendarFragment:
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,calendarFragment).commit();
                        break;
                    case R.id.personalFragment:
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,personalFragment).commit();
                        break;
                }
                return false;
            }
        });

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
