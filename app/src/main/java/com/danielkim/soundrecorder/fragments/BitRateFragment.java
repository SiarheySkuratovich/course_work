package com.danielkim.soundrecorder.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.danielkim.soundrecorder.MySharedPreferences;
import com.danielkim.soundrecorder.R;


public class BitRateFragment extends Fragment{
    public static final String TAG = "BIT_RATE_FRAGMENT_TAG";
    private SeekBar seekBar;
    private TextView textProgress;
    private int seekBarInitVal;
    private int bitRate;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bit_rate, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        seekBar = (SeekBar) view.findViewById(R.id.bit_rate_seek_bar);
        textProgress = (TextView) view.findViewById(R.id.bit_rate_progress);
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
                bitRate = seekBar.getProgress() + seekBarInitVal;
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        MySharedPreferences.setBitRate(getActivity(), bitRate);
    }

    private void setSeekBarRange() {
        seekBarInitVal = 8000;
        seekBar.setMax(800000 - seekBarInitVal);
    }

    private void setSeekBarProgress() {
        bitRate = MySharedPreferences.getBitRate(getActivity());
        if (bitRate != -1) {
            seekBar.setProgress(bitRate - seekBarInitVal);
            textProgress.setText("" + bitRate+ " bps");
        } else {
            seekBar.setProgress(0);
            textProgress.setText("" + seekBarInitVal + " bps");
        }
    }
}
