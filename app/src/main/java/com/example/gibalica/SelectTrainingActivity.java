package com.example.gibalica;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SelectTrainingActivity extends AppCompatActivity {
    private int sec;
    private String mode = "";
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_training);

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

        try{
            Bundle b = getIntent().getExtras();
            mode = b.getString("mode");
            System.out.println("mode:" + mode);
            if (mode != ""){
                switch (mode){
                    case "easy": sec = 20; break;
                    case "medium": sec = 10; break;
                    case "hard": sec = 5; break;
                }
            }
        }
        catch (Exception e){
            System.out.println("exc:" + e);
            sec = -1;
        }
    }

   public void goToSquats(View v) {
        Intent iTraining = new Intent(this, StartTraining.class);
       iTraining.putExtra("pose", "squat");
       if (mode != ""){
           iTraining.putExtra("sec", sec);
       }
        startActivity(iTraining);
    }

    public void goToJumpingJacks(View v) {
        Intent iTraining = new Intent(this, StartTraining.class);
        iTraining.putExtra("pose", "jump");
        if (mode != ""){
            iTraining.putExtra("sec", sec);
        }
        startActivity(iTraining);
    }

    public void goToHand(View v) {
        Intent iTraining = new Intent(this, StartTraining.class);
        iTraining.putExtra("pose", "raisedhand");
        if (mode != ""){
            iTraining.putExtra("sec", sec);
        }
        startActivity(iTraining);
    }


}
