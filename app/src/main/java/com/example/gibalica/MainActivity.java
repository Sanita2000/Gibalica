package com.example.gibalica;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gibalica.mlkitextensions.CameraXLivePreviewActivity;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    public void goToTraining(View v) {
        Intent iTraining = new Intent(this, CameraXLivePreviewActivity.class);
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