package com.example.gibalica;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void goToTraining(View v) {
        Intent iTraining = new Intent(this, CameraActivity.class);
        startActivity(iTraining);
    }
    public void goToCompete(View v) {
        Intent iCompete = new Intent(this, CompeteActivity.class);
        startActivity(iCompete);
    }
    public void goToDayNight(View v) {
        Intent iDayNight = new Intent(this, DayNightActivity.class);
        startActivity(iDayNight);
    }
    public void goToSettings(View v) {
        Intent iSettings = new Intent(this, SettingsActivity.class);
        startActivity(iSettings);
    }

}