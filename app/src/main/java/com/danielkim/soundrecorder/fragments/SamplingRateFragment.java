package com.danielkim.soundrecorder.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;


import com.danielkim.soundrecorder.MySharedPreferences;
import com.danielkim.soundrecorder.R;

import java.util.MissingFormatArgumentException;

import static android.media.MediaRecorder.AudioEncoder.*;

public class SamplingRateFragment extends Fragment {
    public static final String TAG = "SEEK_BAR_FRAGMENT_TAG";
    private static final int MAX = 48000;
    private final int DEFAULT_ENCODER = AAC;
    private int encoder;
    private int MIN;
    RadioGroup radioGroup;
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
        radioGroup = (RadioGroup)view.findViewById(R.id.radioGroup);


        setMinRate();

        if (isAlreadyMax()) {
            radioGroup.check(R.id.radioButtonTwo);
        }


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButtonOne:
                        samplingRate = MIN;
                        break;
                    case R.id.radioButtonTwo:
                        samplingRate = MAX;
                        break;
                }
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
            encoder = Integer.parseInt(MySharedPreferences.getAudioEncoder(getActivity()));
        } else {
            encoder = DEFAULT_ENCODER;
        }
    }

    private void setMinRate() {
        switch (encoder) {
            case AAC:
                MIN = 8000;
                break;
            case AAC_ELD:
                MIN = 16000;
                break;
            case AMR_NB:
                MIN = 8000;
                radioGroup.setEnabled(false);
                break;
            case AMR_WB:
                MIN = 16000;
                radioGroup.setEnabled(false);
                break;
            case HE_AAC:
                MIN = 8000;
                break;
        }
    }

    private boolean isAlreadyMax() {
        if (MySharedPreferences.getSamplingRate(getActivity()) == MAX) {
            return true;
        } else {
            return false;
        }
    }

    /*private void setMax() {
        samplingRate = MySharedPreferences.getSamplingRate(getActivity());
        if (encoder != AMR_NB && encoder != AMR_WB) {
                seekBar.setProgress(samplingRate - seekBarInitVal);
                textProgress.setText("" + samplingRate + " Hz");
        } else {
            seekBar.setProgress(0);
            textProgress.setText("" + seekBarInitVal + " Hz");
        }
    }*/
}
