package com.danielkim.soundrecorder.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.danielkim.soundrecorder.MySharedPreferences;
import com.danielkim.soundrecorder.R;
import com.danielkim.soundrecorder.fragments.AudioSamplingSeekBarFragment;
import com.danielkim.soundrecorder.fragments.SettingsFragment;

import java.util.zip.Inflater;

import static android.media.MediaRecorder.AudioEncoder.AAC;
import static android.media.MediaRecorder.AudioEncoder.AAC_ELD;
import static android.media.MediaRecorder.AudioEncoder.AMR_NB;
import static android.media.MediaRecorder.AudioEncoder.AMR_WB;
import static android.media.MediaRecorder.AudioEncoder.HE_AAC;

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
                .add(R.id.seekBar_container, new AudioSamplingSeekBarFragment(), AudioSamplingSeekBarFragment.TAG)
                .commit();


        /*if(audioSamplingSeekBarFragment.isAdded()) {
            Toast.makeText(this, "!!!!!!!!!!", Toast.LENGTH_LONG).show();
        }*/
    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
