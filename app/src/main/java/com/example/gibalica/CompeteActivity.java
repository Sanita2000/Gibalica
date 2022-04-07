package com.example.gibalica;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CompeteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compete);
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
    }
    public void goToCompetingEasy(View v) {

        Intent iCompeting = new Intent(this, SelectTrainingActivity.class);
        iCompeting.putExtra("mode", "easy");
        startActivity(iCompeting);
    }
    public void goToCompetingMedium(View v) {
        Intent iCompeting = new Intent(this, SelectTrainingActivity.class);
        iCompeting.putExtra("mode", "medium");
        startActivity(iCompeting);
    }
    public void goToCompetingHard(View v) {
        Intent iCompeting = new Intent(this, SelectTrainingActivity.class);
        iCompeting.putExtra("mode", "hard");
        startActivity(iCompeting);
    }
}
