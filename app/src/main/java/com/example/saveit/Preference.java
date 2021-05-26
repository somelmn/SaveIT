package com.example.saveit;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import androidx.annotation.Nullable;
import androidx.preference.ListPreference;

public class Preference extends PreferenceActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

    }

}
