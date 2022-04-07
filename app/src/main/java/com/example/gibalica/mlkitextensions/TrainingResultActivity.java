package com.example.gibalica.mlkitextensions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gibalica.HomeActivity;
import com.example.gibalica.R;

public class TrainingResultActivity extends AppCompatActivity {
    TextView resultTW;
    TextView scoreTw;
    private boolean isCompeting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_result);
        System.out.println("RESULT");
        Bundle b = getIntent().getExtras();
        int score = b.getInt("score");
        int sec = b.getInt("sec");
        if (!(sec == 0 ||sec == -1)){
            resultTW = (TextView)findViewById(R.id.textViewGoal);
           if (10 > score){
                resultTW.setText("Next time!");
            }
            else{
                resultTW.setText("Great job!");
            }
        }

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

    @Override
    public void onBackPressed() {

        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }
}