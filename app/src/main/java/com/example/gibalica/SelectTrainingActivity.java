package com.example.gibalica;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SelectTrainingActivity extends AppCompatActivity {
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

        setContentView(R.layout.activity_select_training);
    }

    public void goToTraining(View v) {
        Intent iTraining = new Intent(this, StartTraining.class);
        startActivity(iTraining);
    }

    public void goToJumpingJacks(View v) {
        Intent iTraining = new Intent(this, StartTraining.class);
        iTraining.putExtra("pose", "jump");
        startActivity(iTraining);
    }

    public void goToHand(View v) {
        Intent iTraining = new Intent(this, StartTraining.class);
        iTraining.putExtra("pose", "raisedhand");
        startActivity(iTraining);
    }


}
