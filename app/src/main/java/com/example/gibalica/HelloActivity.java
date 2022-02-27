package com.example.gibalica;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class HelloActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
    }

    public void goToGuide(View v) {
        Intent iTraining = new Intent(this, GuideActivity.class);
        startActivity(iTraining);
    }

    public void goToHome(View v) {
        Intent iTraining = new Intent(this, HomeActivity.class);
        startActivity(iTraining);
    }
}
