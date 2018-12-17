package com.danielkim.soundrecorder.fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.danielkim.soundrecorder.MySharedPreferences;
import com.danielkim.soundrecorder.R;

import java.util.zip.Inflater;

import static android.media.MediaRecorder.AudioEncoder.*;

public class AudioSamplingSeekBarFragment extends Fragment {
    public static final String TAG = "SEEK_BAR_FRAGMENT_TAG";
    public static final String STATE_FORMAT = "format";
    private final int DEFAULT_FORMAT = AAC;
    private int format;
    private int seekBarInitVal;
    SeekBar seekBar;
    TextView textProgress;
    private int seekBarProgress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_audio_sampling_seekbar, container,false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setAudioEncoder();

        seekBar = (SeekBar)view.findViewById(R.id.sample_rate_seek_bar);
        textProgress = (TextView) view.findViewById(R.id.progress);

        setSeekBarRange();
        setSeekBarProgress();


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                i += seekBarInitVal;
                textProgress.setText("" + i + " Hz");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBarProgress = seekBar.getProgress();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        MySharedPreferences.setSamplingRate(getActivity(), seekBarProgress);
    }

    private void setAudioEncoder() {
        if (getArguments() != null){
            format = getArguments().getInt(STATE_FORMAT);
        } else if (MySharedPreferences.getAudioEncoder(getActivity()) != null){
            format = Integer.parseInt(MySharedPreferences.getAudioEncoder(getActivity()));
        } else {
            format = DEFAULT_FORMAT;
        }
    }

    private void setSeekBarRange() {
        switch (format) {
            case AAC:
                seekBarInitVal = 8000;
                seekBar.setMax(40000);
                break;
            case AAC_ELD:
                seekBarInitVal = 16000;
                seekBar.setMax(32000);
                break;
            case AMR_NB:
                seekBarInitVal = 8000;
                seekBar.setEnabled(false);
                break;
            case AMR_WB:
                seekBarInitVal = 16000;
                seekBar.setEnabled(false);
                break;
            case HE_AAC:
                seekBarInitVal = 8000;
                seekBar.setMax(40000);
                break;
        }
    }

    private void setSeekBarProgress() {
        seekBarProgress = MySharedPreferences.getSamplingRate(getActivity());
        if (format != AMR_NB || format != AMR_WB) {
            if (seekBarProgress != -1) {
                seekBar.setProgress(seekBarProgress);
                textProgress.setText("" + seekBarProgress + " Hz");
            } else {
                textProgress.setText("" + seekBarInitVal + " Hz");
            }
        }
    }
}
