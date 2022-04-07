package com.example.gibalica;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class HelloActivity extends AppCompatActivity {

    TextToSpeech tts;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        SharedPreferences sp = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        final String fontSize = sp.getString("fontSize", "small");
        final boolean isTTSChecked = sp.getBoolean("isTTSChecked", false);

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
        setContentView(R.layout.activity_hello);

        if(isTTSChecked) {
            tts = new TextToSpeech(HelloActivity.this, new TextToSpeech.OnInitListener() {

                @Override
                public void onInit(int status) {
                    // TODO Auto-generated method stub
                    if (status == TextToSpeech.SUCCESS) {
                        int result = tts.setLanguage(Locale.US);
                        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Log.e("error", "This Language is not supported");
                        } else {
                            ConvertTextToSpeech();
                        }
                    } else
                        Log.e("error", "Initilization Failed!");
                }
            });
        }

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub

        if(tts != null){

            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }

    public void onClick(View v){

        ConvertTextToSpeech();

    }

    private void ConvertTextToSpeech() {
        // TODO Auto-generated method stub
        text = "Welcome to Gibalica, interactive tool for practicing orientation in a fun way";
        if(text==null||"".equals(text))
        {
            text = "Content not available";
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }else{
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }




    public void goToGuide(View v) {
        Intent iGuide = new Intent(this, GuideActivity.class);
        startActivity(iGuide);
    }

    public void goToHome(View v) {
        Intent iHome = new Intent(this, HomeActivity.class);
        startActivity(iHome);
    }
}
