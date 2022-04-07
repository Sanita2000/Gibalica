package com.example.gibalica;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.gibalica.mlkitextensions.CameraXLivePreviewActivity;


public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        SharedPreferences sp = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        final String fontSize = sp.getString("fontSize", "small");

        switch (fontSize){
            case "large":
                this.setTheme(R.style.Theme_Gibalica_Large_Font);
                break;
            case "medium":
                this.setTheme(R.style.Theme_Gibalica_Medium_Font);
                break;
            default:
                this.setTheme(R.style.Theme_Gibalica);
        }
        setContentView(R.layout.activity_home);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            },200);
            return;
        }
    }

    public void goToSelectTraining(View v) {
        Intent iTraining = new Intent(this, SelectTrainingActivity.class);
        startActivity(iTraining);
    }
    public void goToTraining(View v) {
        Intent iTraining = new Intent(this, SelectTrainingActivity.class);
        startActivity(iTraining);
    }
    public void goToCompete(View v) {
        Intent iTraining = new Intent(this, CompeteActivity.class);
        startActivity(iTraining);
    }
    public void goToDayNight(View v) {
        Intent iDayNight = new Intent(this, DayNightActivity.class);
        startActivity(iDayNight);
    }
    public void goToSettings(View v) {
        Intent iSettings = new Intent(this, SettingsActivity.class);
        startActivity(iSettings);
    }
    public void goToInfo(View v) {
        Intent iInfo = new Intent(this, InformationActivity.class);
        startActivity(iInfo);
    }

}