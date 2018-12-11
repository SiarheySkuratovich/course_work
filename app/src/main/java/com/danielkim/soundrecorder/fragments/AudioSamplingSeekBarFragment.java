package com.danielkim.soundrecorder.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import com.danielkim.soundrecorder.R;

public class AudioSamplingSeekBarFragment extends PreferenceFragment{
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.seekbar_fragment);
    }

    public void
}
