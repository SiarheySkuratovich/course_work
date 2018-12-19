package com.danielkim.soundrecorder.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.danielkim.soundrecorder.R;
import com.danielkim.soundrecorder.fragments.BitRateFragment;
import com.danielkim.soundrecorder.fragments.SamplingRateFragment;
import com.danielkim.soundrecorder.fragments.SettingsFragment;

/**
 * Created by Daniel on 5/22/2017.
 */

public class SettingsActivity extends android.support.v7.app.ActionBarActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setPopupTheme(R.style.ThemeOverlay_AppCompat_Light);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.action_settings);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        getFragmentManager()
                .beginTransaction()
                .add(R.id.preferences_container, new SettingsFragment())
                .commit();


        getFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_seekBar_container, new SamplingRateFragment(), SamplingRateFragment.TAG)
                .commit();



    }
}
