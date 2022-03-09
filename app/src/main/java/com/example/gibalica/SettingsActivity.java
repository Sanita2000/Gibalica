package com.example.gibalica;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class SettingsActivity extends AppCompatActivity {
    SharedPrefs sp;
    Switch tts, nightMode, contrast;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        // use sp to change settings values
        sp  = new SharedPrefs(this);

        tts = (Switch) findViewById(R.id.switch1);
        nightMode = (Switch) findViewById(R.id.switch2);
        contrast = (Switch) findViewById(R.id.switch3);

        tts.setOnCheckedChangeListener(listener);
        nightMode.setOnCheckedChangeListener(listener);
        contrast.setOnCheckedChangeListener(listener);
    }

    CompoundButton.OnCheckedChangeListener listener = (buttonView, isChecked) -> {
        if(buttonView.isChecked()){
            Toast.makeText(SettingsActivity.this, buttonView.getText() , Toast.LENGTH_SHORT).show();
        }else{

        }

    };
}
