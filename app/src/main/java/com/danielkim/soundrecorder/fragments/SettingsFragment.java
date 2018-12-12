package com.danielkim.soundrecorder.fragments;

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

/**
 * Created by Daniel on 5/22/2017.
 */

public class SettingsFragment extends PreferenceFragment {
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
                embedSeekBarWithFormat((String) newValue);
                return true;
            }
        });
    }
    public void embedSeekBarWithFormat(String format) {
        AudioSamplingSeekBarFragment audioSamplingSeekBarFragment = new AudioSamplingSeekBarFragment();
        Bundle bundle = new Bundle();
        if (audioSamplingSeekBarFragment.isAdded()) {
            audioSamplingSeekBarFragment.onDestroy();
        } else {
            bundle.putInt("format", Integer.parseInt(format));
            audioSamplingSeekBarFragment.setArguments(bundle);


            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.seekBar_container, audioSamplingSeekBarFragment)
                    .commit();
        }
    }
}
