package com.example.gibalica.mlkitextensions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.gibalica.HomeActivity;
import com.example.gibalica.R;

public class TrainingResultActivity extends AppCompatActivity {

    Button navigationBtn;
    TextView scoreTw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_result);
        Bundle b = getIntent().getExtras();
        int score = b.getInt("score");
        System.out.println("RESULT: " + score);
        scoreTw = (TextView)findViewById(R.id.score2TextView);
        if (scoreTw != null){
            scoreTw.setText("" + score);
            System.out.println("nem nulla");
        }else{
            System.out.println("hopps");
            System.out.println("nullla");
        }
    }

    public void goToMenu(View v){
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }
}