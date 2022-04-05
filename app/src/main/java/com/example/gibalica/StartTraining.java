package com.example.gibalica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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