package com.danielkim.soundrecorder;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Daniel on 5/22/2017.
 */

public class MySharedPreferences {
    private static String ENCODER = "encoder";
    private static String SAMPLING_RATE = "sampling_rate";
    private static String BIT_RATE  = "bit_rate";





    public static void setAudioEncoder(Context context, String usersChosenFormat) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ENCODER, usersChosenFormat);
        editor.apply();
    }

    public static String getAudioEncoder(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(ENCODER, null);
    }

    public static void setSamplingRate(Context context, int samplingRate) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(SAMPLING_RATE, samplingRate);
        editor.apply();
    }

    public static int getSamplingRate(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(SAMPLING_RATE, -1);
    }

    public static void setBitRate(Context context, int bitRate) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(BIT_RATE, bitRate);
        editor.apply();
    }

    public static int getBitRate(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(BIT_RATE, -1);
    }


}
