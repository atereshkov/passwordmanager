package com.tereshkoff.passwordmanager.activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import com.tereshkoff.passwordmanager.R;


public class PrefActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings_pref);



    }
}
