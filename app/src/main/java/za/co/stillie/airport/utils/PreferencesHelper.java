package za.co.stillie.airport.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import za.co.stillie.airport.BuildConfig;

/**
 * This class handles all the values which are stored/persisted.
 *
 * @version 1.0.
 */
public class PreferencesHelper {

    private static final String PREFS_NAME = BuildConfig.APPLICATION_ID + ".preferences";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor prefsEditor;

    @SuppressLint("CommitPrefEdits")
    public PreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);
        prefsEditor = sharedPreferences.edit();
    }

    public boolean contains(String key) {
        return this.sharedPreferences.contains(key);
    }

    public Long getLong(String key) {
        return sharedPreferences.getLong(key, 0);
    }

    public void putLong(String key, Long value) {
        prefsEditor.putLong(key, value);
        prefsEditor.apply();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public String getString(String key, String aDefaultValue) {
        return sharedPreferences.getString(key, aDefaultValue);
    }

    public void putString(String key, String value) {
        prefsEditor.putString(key, value);
        prefsEditor.apply();
    }

    public float getFloat(String key) {
        return sharedPreferences.getFloat(key, 0F);
    }

    public void putFloat(String key, Float value) {
        prefsEditor.putFloat(key, value);
        prefsEditor.apply();
    }

    public void putBoolean(String key, Boolean value) {
        prefsEditor.putBoolean(key, value);
        prefsEditor.apply();
    }

    public void removeKey(String key) {
        if (TextUtils.isEmpty(key)) {
            throw new IllegalArgumentException("Key cannot be null or empty");
        }
        prefsEditor.remove(key);
    }

    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public int getInt(String key) {
        return sharedPreferences.getInt(key, 0);
    }

    public void putInt(String key, int value) {
        prefsEditor.putInt(key, value);
        prefsEditor.apply();
    }

    public void clear() {
        prefsEditor.clear();
        prefsEditor.apply();
    }

    public void putList(String key, List<String> data) {
        List<String> textList = new ArrayList<>(data);
        String jsonText = new Gson().toJson(textList);
        prefsEditor.putString(key, jsonText);
        prefsEditor.apply();
    }

    public List<String> getList(String key) {
        String jsonText = sharedPreferences.getString(key, null);
        String[] text = new Gson().fromJson(jsonText, String[].class);
        List<String> alStrings = new ArrayList<>();
        if (text != null && text.length > 0) {
            alStrings.addAll(Arrays.asList(text));
        }
        return alStrings;
    }

    public void putObject(String key, Object value) {
        String json = new Gson().toJson(value);
        prefsEditor.putString(key, json);
        prefsEditor.apply();
    }

    public Object getObject(String key, Class<?> clazz) {
        String json = sharedPreferences.getString(key, "");
        return new Gson().fromJson(json, clazz);
    }
}
