package com.example.clothual.UI.welcome;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clothual.R;
import com.google.android.material.snackbar.Snackbar;

public class WelcomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);

    }

    @Override
    public void onBackPressed(){
        //Snackbar
        View view = findViewById(android.R.id.content);
        Snackbar.make(view, R.string.close_app, Snackbar.LENGTH_SHORT).show();
    }
}