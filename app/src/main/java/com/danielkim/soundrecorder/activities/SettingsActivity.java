package com.danielkim.soundrecorder.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.danielkim.soundrecorder.R;
import com.danielkim.soundrecorder.fragments.AudioSamplingSeekBarFragment;
import com.danielkim.soundrecorder.fragments.SettingsFragment;

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
                .replace(R.id.container, new SettingsFragment())
                .commit();
    }

    public void setAudioSamplingSeekBar(Context context, String usersChosenFormat) {
        AudioSamplingSeekBarFragment audioSamplingSeekBarFragment = new AudioSamplingSeekBarFragment();
        switch (Integer.parseInt(usersChosenFormat)) {
            case AAC:
                embedSeekBarWithFormat(audioSamplingSeekBarFragment, AAC);
                break;
            case AAC_ELD:
                embedSeekBarWithFormat(audioSamplingSeekBarFragment, AAC_ELD);
                break;
            case AMR_NB:
                embedSeekBarWithFormat(audioSamplingSeekBarFragment, AMR_NB);
                break;
            case AMR_WB:
                embedSeekBarWithFormat(audioSamplingSeekBarFragment, AMR_WB);
                break;
            case HE_AAC:
                embedSeekBarWithFormat(audioSamplingSeekBarFragment, HE_AAC);
                break;
        }
    }
    private void embedSeekBarWithFormat(AudioSamplingSeekBarFragment audioSamplingSeekBarFragment, int format) {
        Bundle bundle = new Bundle();
        if (audioSamplingSeekBarFragment.isAdded()) {
            audioSamplingSeekBarFragment.onDestroy();
        } else {
            bundle.putInt("format", format);
            audioSamplingSeekBarFragment.setArguments(bundle);
            getFragmentManager()
                    .beginTransaction()
                    .add(audioSamplingSeekBarFragment, "audioSamplingSeekBarFragment")
                    .commit();
        }
    }
}
