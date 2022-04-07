package com.example.gibalica;

import android.content.Intent;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class SettingsActivity extends AppCompatActivity {
    SharedPreferences sp;
    TextToSpeech textToSpeech;
    Switch tts, nightMode, contrast;
    SeekBar fontSizeBar;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // Saving state of our app
        // using SharedPreferences
        sp = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sp.edit();
        //final boolean isDarkModeOn = sp.getBoolean("isDarkModeOn", false);
        //final boolean isChecked = sp.getBoolean("isChecked", false);
        final boolean isTTSChecked = sp.getBoolean("isTTSChecked", false);
        final String fontSize = sp.getString("fontSize", "small");
        final int seekBarProgress = sp.getInt("seekBarProgress", 0);

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

        setContentView(R.layout.activity_settings);


        tts = (Switch) findViewById(R.id.switch1);
        nightMode = (Switch) findViewById(R.id.switch2);
        contrast = (Switch) findViewById(R.id.switch3);
        fontSizeBar = (SeekBar) findViewById(R.id.seekBar3);


        // When user reopens the app
        // after applying dark/light mode
        /*if (isDarkModeOn) {
            nightMode.setText("Disable Dark Mode");
        }
        else {
            nightMode.setText("Enable Dark Mode");
        } */

        //nightMode.setChecked(isChecked);
        tts.setChecked(isTTSChecked);
        fontSizeBar.setProgress(seekBarProgress);

        /*nightMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isCheckedNow) {
                if (!isCheckedNow) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor.putBoolean("isDarkModeOn", false);
                    editor.putBoolean("isChecked", false);
                    editor.apply();
                    // change text of Button
                    nightMode.setText("Enable Dark Mode");
                }
                else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor.putBoolean("isDarkModeOn", true);
                    editor.putBoolean("isChecked", true);
                    editor.apply();
                    // change text of Button
                    nightMode.setText("Disable Dark Mode");
                }
            }
        }); */

        tts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isCheckedNow) {
                editor.putBoolean("isTextToSpeechOn", isCheckedNow);
                editor.putBoolean("isTTSChecked", isCheckedNow);
                editor.apply();
                //if(isCheckedNow){
                //start tts
                //    textToSpeech.speak("TESTING", TextToSpeech.QUEUE_FLUSH, null, null);
                //}
            }
        });

        fontSizeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                switch(progress) {
                    case 2:
                        editor.putInt("seekBarProgress", 2);
                        editor.putString("fontSize", "large");
                        editor.apply();
                        break;
                    case 1:
                        editor.putInt("seekBarProgress", 1);
                        editor.putString("fontSize", "medium");
                        editor.apply();
                        break;
                    default:
                        editor.putInt("seekBarProgress", 0);
                        editor.putString("fontSize", "small");
                        editor.apply();
                }
                recreate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        nightMode.setOnCheckedChangeListener(listener);
        contrast.setOnCheckedChangeListener(listener);
    }

    CompoundButton.OnCheckedChangeListener listener = (buttonView, isChecked) -> {
        if(buttonView.isChecked()){
            Toast.makeText(SettingsActivity.this, buttonView.getText() , Toast.LENGTH_SHORT).show();
        }
    };
    public void goToGuide(View v) {
        Intent iGuide = new Intent(this, GuideActivity.class);
        startActivity(iGuide);
    }
}