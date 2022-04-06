package com.example.gibalica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.example.gibalica.mlkitextensions.CameraXLivePreviewActivity;

public class StartTraining extends AppCompatActivity {
    private static int TIMER = 3000;
    private TextView tw;
    private  String poseName;

    private CountDownTimer Timer = new CountDownTimer(TIMER, 1000) {

        public void onTick(long millisUntilFinished) {
            tw.setText("" + millisUntilFinished / 1000);
        }

        public void onFinish() {
            tw.setText("START!");
            Intent iTraining = new Intent(getApplicationContext(), CameraXLivePreviewActivity.class);
            iTraining.putExtra("pose", poseName);
            startActivity(iTraining);
            //tw.setText("" + TIMER/1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        setContentView(R.layout.activity_start_training);
        tw = findViewById(R.id.time);
        tw.setText("" + TIMER/1000);
        Bundle b = getIntent().getExtras();
        this.poseName = b.getString("pose");
    }

    public void goToTraining(View v) {
        Timer.start();
    }
}