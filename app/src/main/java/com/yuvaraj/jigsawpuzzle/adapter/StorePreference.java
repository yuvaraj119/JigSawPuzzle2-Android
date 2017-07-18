package com.yuvaraj.jigsawpuzzle.adapter;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Colan Infotech.
 */

public class StorePreference {
    private Context mContext;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mPreferenceEditor;

    public StorePreference(Context aContext) {
        mContext = aContext;
        mSharedPreferences = mContext.getSharedPreferences("JIGSAW", Context.MODE_PRIVATE);
        mPreferenceEditor = mSharedPreferences.edit();
    }

    public StorePreference setString(String key, String value) {
        mPreferenceEditor.putString(key, value);
        mPreferenceEditor.commit();
        return null;
    }

    public void setInt(String key, int value) {
        mPreferenceEditor.putInt(key, value);
        mPreferenceEditor.commit();
    }

    public String getStringValue(String key) {
        String aName = "";
        aName = mSharedPreferences.getString(key, null);
        return aName == null ? "" : aName;
    }

    public int getIntValue(String key) {
        return mSharedPreferences.getInt(key, -1);
    }

    public void setBoolean(String key, boolean value) {
        mPreferenceEditor.putBoolean(key, value);
        mPreferenceEditor.commit();
    }

    public boolean getBooleanValue(String key) {
        return mSharedPreferences.getBoolean(key, false);
    }


    public void clearEditor(String name) {



        SharedPreferences sharedPrefs = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.clear();
        editor.commit();

        //Setting it for Remember me
    }

}