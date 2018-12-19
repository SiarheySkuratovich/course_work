package com.danielkim.soundrecorder.fragments;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.danielkim.soundrecorder.BuildConfig;
import com.danielkim.soundrecorder.MySharedPreferences;
import com.danielkim.soundrecorder.R;
import com.danielkim.soundrecorder.activities.SettingsActivity;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Daniel on 5/22/2017.
 */

public class SettingsFragment extends PreferenceFragment {
    private static Logger log = Logger.getLogger(SettingsFragment.class.getName());
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        CheckBoxPreference highQualityPref = (CheckBoxPreference) findPreference(getResources().getString(R.string.pref_high_quality_key));
        highQualityPref.setChecked(MySharedPreferences.getPrefHighQuality(getActivity()));
        highQualityPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                MySharedPreferences.setPrefHighQuality(getActivity(), (boolean) newValue);
                return true;
            }
        });

        Preference aboutPref = findPreference(getString(R.string.pref_about_key));
        aboutPref.setSummary(getString(R.string.pref_about_desc, BuildConfig.VERSION_NAME));
                    aboutPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
            public boolean onPreferenceClick(Preference preference) {
                LicensesFragment licensesFragment = new LicensesFragment();
                licensesFragment.show(((SettingsActivity)getActivity()).getSupportFragmentManager().beginTransaction(), "dialog_licenses");
                return true;
            }
        });


        ListPreference outputFormatPref = (ListPreference) findPreference(getResources().getString(R.string.key_encoder));
        outputFormatPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                MySharedPreferences.setAudioEncoder(getActivity(), (String) newValue);
                replaceSampleRateSeekBar();
                if (isAMR((String) newValue)) {
                    embadBitRateSeekBar();
                } else {
                    deleteBitRateSeekBar();
                }
                return true;
            }
        });
    }

    public void replaceSampleRateSeekBar() {
        try {
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.fragment_seekBar_container, new SamplingRateFragment(), SamplingRateFragment.TAG);
            transaction.commit();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Exception: ", e);
        }
    }
    
    public void embadBitRateSeekBar() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (manager.findFragmentByTag(BitRateFragment.TAG) != null) {
            transaction.remove(manager.findFragmentByTag(BitRateFragment.TAG));
        }
        transaction.add(R.id.fragment_bit_rate_container, new BitRateFragment(), BitRateFragment.TAG);
        transaction.commit();

    }
    
    private boolean isAMR(String format) {
        int formatInt = Integer.parseInt(format);
        return formatInt == MediaRecorder.AudioEncoder.AMR_NB || formatInt == MediaRecorder.AudioEncoder.AMR_WB;
    }

    private void deleteBitRateSeekBar() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (manager.findFragmentByTag(BitRateFragment.TAG) != null) {
            transaction.remove(manager.findFragmentByTag(BitRateFragment.TAG));
            transaction.commit();
        }
    }
}
