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
        Intent iGuide = new Intent(this, GuideActivity.class);
        startActivity(iGuide);
    }

    public void goToHome(View v) {
        Intent iHome = new Intent(this, HomeActivity.class);
        startActivity(iHome);
    }
}
