package com.example.gibalica;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class InformationActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_information);

    }
}
