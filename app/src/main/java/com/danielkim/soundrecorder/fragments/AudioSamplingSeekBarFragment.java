package com.danielkim.soundrecorder.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.danielkim.soundrecorder.R;

import java.util.zip.Inflater;

public class AudioSamplingSeekBarFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_audio_sampling_seekbar, container,false);
    }
}
