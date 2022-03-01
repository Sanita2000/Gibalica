package com.example.gibalica;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent iHello = new Intent(this, HelloActivity.class);
        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {

            };

            public void onFinish(){
                startActivity(iHello);
            }
        }.start();

    }



}
