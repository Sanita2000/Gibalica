package com.example.gibalica;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class CompeteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compete);
    }
    public void goToCompetingEasy(View v) {
        Intent iCompeting = new Intent(this, CompetingActivity.class);
        startActivity(iCompeting);
    }
    public void goToCompetingMedium(View v) {
        Intent iCompeting = new Intent(this, CompetingActivity.class);
        startActivity(iCompeting);
    }
    public void goToCompetingHard(View v) {
        Intent iCompeting = new Intent(this, CompetingActivity.class);
        startActivity(iCompeting);
    }
}
