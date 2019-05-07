package com.danielkim.soundrecorder.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.danielkim.soundrecorder.MySharedPreferences;
import com.danielkim.soundrecorder.R;

import static android.media.MediaRecorder.AudioEncoder.*;

public class SamplingRateFragment extends Fragment {
    public static final String TAG = "SEEK_BAR_FRAGMENT_TAG";
    private static final String TAG2 = "MyApp";
    private static final int MAX = 48000;
    private final int DEFAULT_ENCODER = AAC;
    private int encoder;
    private int MIN;
    RadioGroup radioGroup;
    private int samplingRate;
    RadioButton radioButton1;
    RadioButton radioButton2;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_audio_sampling, container,false);
    }


    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setAudioEncoder();
        radioGroup = (RadioGroup)view.findViewById(R.id.radioGroup);
        radioButton1 = (RadioButton)getView().findViewById(R.id.radioButtonOne);
        radioButton2 = (RadioButton)getView().findViewById(R.id.radioButtonTwo);


        setMinRate();

        if(isAMR()) {
            radioGroup.check(R.id.radioButtonOne);
            samplingRate = MIN;
            radioButton2.setEnabled(false);
        } else if (isAlreadyMax()) {
            radioGroup.check(R.id.radioButtonTwo);
            samplingRate = MAX;
        } else {
            radioGroup.check(R.id.radioButtonOne);
            samplingRate = MIN;
        }

        /*if (isAlreadyMax() && !isAMR()) {
            radioGroup.check(R.id.radioButtonTwo);
            samplingRate = MAX;
        } else {
            radioGroup.check(R.id.radioButtonOne);
        }*/


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
    public void onStop() {
        super.onStop();
        MySharedPreferences.setSamplingRate(getActivity(), samplingRate);
        Log.i(TAG2, Integer.toString(MySharedPreferences.getSamplingRate(getActivity())));
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
                radioGroup.check(R.id.radioButtonOne);
                radioButton2.setEnabled(false);
                break;
            case AMR_WB:
                MIN = 16000;
                radioGroup.check(R.id.radioButtonOne);
                radioButton2.setEnabled(false);
                break;
            case HE_AAC:
                MIN = 8000;
                break;
        }
        radioButton1.setText(MIN + " Hz");
    }

    private boolean isAlreadyMax() {
        return (MySharedPreferences.getSamplingRate(getActivity()) == MAX);
    }

    private boolean isAMR() {
       return (encoder == AMR_NB || encoder == AMR_WB);
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
