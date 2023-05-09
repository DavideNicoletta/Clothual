package com.example.clothual.Util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class SharePreferenceReadWrite {

    private final Application application;

    public SharePreferenceReadWrite(Application application) {
        this.application = application;
    }

    public void writeStringData(String sharedPreferencesFileName, String key, String value) {
        SharedPreferences sharedPref = application.getSharedPreferences(sharedPreferencesFileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void writeBooleanData(String sharedPreferencesFileName, String key, boolean value) {
        SharedPreferences sharedPref = application.getSharedPreferences(sharedPreferencesFileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public void writeStringSetData(String sharedPreferencesFileName, String key, Set<String> value) {
        SharedPreferences sharedPref = application.getSharedPreferences(sharedPreferencesFileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putStringSet(key, value);
        editor.apply();
    }

    public String readStringData(String sharedPreferencesFileName, String key) {
        SharedPreferences sharedPref = application.getSharedPreferences(sharedPreferencesFileName,
                Context.MODE_PRIVATE);
        return sharedPref.getString(key, null);
    }

    public boolean readBooleanData(String sharedPreferencesFileName, String key) {
        SharedPreferences sharedPref = application.getSharedPreferences(sharedPreferencesFileName,
                Context.MODE_PRIVATE);
        return sharedPref.getBoolean(key, false);
    }

    public Set<String> readStringSetData(String sharedPreferencesFileName, String key) {
        SharedPreferences sharedPref = application.getSharedPreferences(sharedPreferencesFileName,
                Context.MODE_PRIVATE);
        return sharedPref.getStringSet(key, null);
    }

    public void deleteAll(String sharedPreferencesFileName) {
        SharedPreferences sharedPref = application.getSharedPreferences(sharedPreferencesFileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();
    }

}
