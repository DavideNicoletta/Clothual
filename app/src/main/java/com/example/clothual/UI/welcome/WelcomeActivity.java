package com.example.clothual.UI.welcome;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clothual.R;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);

    }
    //disable backButton
    @Override
    public void onBackPressed(){
        //Toast
        //Toast.makeText(getApplicationContext(),R.string.close_app, Toast.LENGTH_SHORT).show();

        //Snackbar
        View view = findViewById(android.R.id.content);
        Snackbar.make(view, R.string.close_app, Snackbar.LENGTH_SHORT).show();
    }
}