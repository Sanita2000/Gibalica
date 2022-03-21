package com.example.gibalica;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NotificationCompat;

public class SettingsActivity extends AppCompatActivity {
    SharedPrefs sp;
    Switch tts, nightMode, contrast;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        // use sp to change settings values
        //sp  = new SharedPrefs(this);

        tts = (Switch) findViewById(R.id.switch1);
        nightMode = (Switch) findViewById(R.id.switch2);
        contrast = (Switch) findViewById(R.id.switch3);

        // Saving state of our app
        // using SharedPreferences
        SharedPreferences sp = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sp.edit();
        final boolean isDarkModeOn = sp.getBoolean("isDarkModeOn", false);
        final boolean isChecked = sp.getBoolean("isChecked", false);

        // When user reopens the app
        // after applying dark/light mode
        if (isDarkModeOn) {
            nightMode.setText("Disable Dark Mode");
        }
        else {
            nightMode.setText("Enable Dark Mode");
        }

        nightMode.setChecked(isChecked);

        nightMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isCheckedNow) {
                if (!isCheckedNow) {
                    // if dark mode is on it
                    // will turn it off
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    // it will set isDarkModeOn
                    // boolean to false
                    editor.putBoolean("isDarkModeOn", false);
                    editor.putBoolean("isChecked", false);
                    editor.apply();

                    // change text of Button
                    nightMode.setText("Enable Dark Mode");
                }
                else {
                    // if dark mode is off
                    // it will turn it on
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                    // it will set isDarkModeOn
                    // boolean to true
                    editor.putBoolean("isDarkModeOn", true);
                    editor.putBoolean("isChecked", true);
                    editor.apply();

                    // change text of Button
                    nightMode.setText("Disable Dark Mode");
                }
            }
        });

        tts.setOnCheckedChangeListener(listener);
        //nightMode.setOnCheckedChangeListener(listener);
        contrast.setOnCheckedChangeListener(listener);
    }


    CompoundButton.OnCheckedChangeListener listener = (buttonView, isChecked) -> {
        if(buttonView.isChecked()){
            Toast.makeText(SettingsActivity.this, buttonView.getText() , Toast.LENGTH_SHORT).show();
        }
    };

}
