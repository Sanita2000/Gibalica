package com.example.gibalica;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SelectTrainingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
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
