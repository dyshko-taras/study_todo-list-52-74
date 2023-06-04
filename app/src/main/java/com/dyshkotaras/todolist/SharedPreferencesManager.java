package com.dyshkotaras.todolist;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    private static final String PREF_NAME = "MyPrefs";
    private static final String LAST_WEBSITE_KEY = "last_website";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public String getLastWebsite() {
        return sharedPreferences.getString(LAST_WEBSITE_KEY, null);
    }

    public void saveLastWebsite(String websiteUrl) {
        editor.putString(LAST_WEBSITE_KEY, websiteUrl);
        editor.apply();
    }
}
