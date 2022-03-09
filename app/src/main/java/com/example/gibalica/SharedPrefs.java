package com.example.gibalica;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context cont;

    int PRIVATE_MODE=0;

    private static final String FOLDER_NAME = "settings";
    private static final String THEME = "theme";
    private static final String FONT = "font";
    private static final String TTS = "t_t_s";

    public SharedPrefs(Context context){
        this.cont = context;
        pref = cont.getSharedPreferences(FOLDER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    public void setTheme(String theme){
        editor.putString(THEME,theme);
        editor.commit();
    }
    public String getTheme(Context context){
        String theme = pref.getString(THEME, "Theme");
        return theme;
    }

    public void setFont(String font){
        editor.putString(FONT,font);
        editor.commit();
    }
    public String getFont(Context context){
        String font = pref.getString(FONT, null);
        return font;
    }
    public void setTTS(boolean tts){
        editor.putBoolean(TTS, tts);
        editor.commit();
    }
    public boolean getTTS(Context context){
        boolean tts = pref.getBoolean(TTS, true);
        return tts;
    }

}
