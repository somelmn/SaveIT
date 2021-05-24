package com.example.saveit;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;


public class Preference extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
        LoadSettings();
    }
    private void LoadSettings(){
        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(this);
        boolean check_night=sp.getBoolean("NIGHT",false);
        if(check_night){
            getListView().setBackgroundColor(Color.parseColor("#222222"));
        }else{
            getListView().setBackgroundColor(Color.parseColor("#fffffff"));
        }

        CheckBoxPreference check_night_instant=(CheckBoxPreference)findPreference("NIGHT");
        check_night_instant.setOnPreferenceChangeListener(new android.preference.Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(android.preference.Preference prefs, Object obj) {
                boolean yes=(boolean)obj;

                if(yes){
                    getListView().setBackgroundColor(Color.parseColor("#222222"));

                }else{
                    getListView().setBackgroundColor(Color.parseColor("#fffffff"));
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        LoadSettings();
        super.onResume();
    }
}
