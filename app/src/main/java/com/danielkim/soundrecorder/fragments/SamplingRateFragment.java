package com.danielkim.soundrecorder.fragments;

import android.app.Fragment;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;


import com.danielkim.soundrecorder.MySharedPreferences;
import com.danielkim.soundrecorder.R;

import static android.media.MediaRecorder.AudioEncoder.*;

public class SamplingRateFragment extends Fragment {
    public static final String TAG = "SEEK_BAR_FRAGMENT_TAG";
    private final int DEFAULT_FORMAT = AAC;
    private int format;
    private int seekBarInitVal;
    SeekBar seekBar;
    TextView textProgress;
    private int samplingRate;

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
                samplingRate = seekBar.getProgress() + seekBarInitVal;
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        MySharedPreferences.setSamplingRate(getActivity(), samplingRate);
    }

    private void setAudioEncoder() {
        if (MySharedPreferences.getAudioEncoder(getActivity()) != null){
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
        samplingRate = MySharedPreferences.getSamplingRate(getActivity());
        if (format != AMR_NB && format != AMR_WB && samplingRate != -1) {
            // тута
                seekBar.setProgress(samplingRate - seekBarInitVal);
                textProgress.setText("" + samplingRate + " Hz");
        } else {
            seekBar.setProgress(0);
            textProgress.setText("" + seekBarInitVal + " Hz");
        }
    }
}
