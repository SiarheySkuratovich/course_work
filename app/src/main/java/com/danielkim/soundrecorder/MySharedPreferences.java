package com.danielkim.soundrecorder;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Daniel on 5/22/2017.
 */

public class MySharedPreferences {
    private static String PREF_HIGH_QUALITY = "pref_high_quality";
    private static String ENCODER = "encoder";



    public static void setPrefHighQuality(Context context, boolean isEnabled) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(PREF_HIGH_QUALITY, isEnabled);
        editor.apply();
    }

    public static boolean getPrefHighQuality(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(PREF_HIGH_QUALITY, false);
    }

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

}