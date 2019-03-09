package com.userapp.driveroncall.utility;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    public static PreferenceManager preferenceManager;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    public static PreferenceManager getInstance() {
        return preferenceManager;
    }

    public PreferenceManager(Context context) {
        preferenceManager = this;
        mSharedPreferences = context.getSharedPreferences(VariableBag.PREF_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public void clearPreferences() {
        mEditor.clear();
        mEditor.commit();
    }

    public void removePref(Context context, String keyToRemove) {
        mSharedPreferences = context.getSharedPreferences(VariableBag.PREF_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mEditor.remove(keyToRemove);
        mEditor.commit();
    }

    /*set preference for registration*/




    public String getRegisteredUserId() {
        String strUserId = mSharedPreferences.getString(VariableBag.USER_ID, "0");
        return strUserId;
    }

    public void setRegisteredUserId(String strUserId) {
        mEditor.putString(VariableBag.USER_ID, strUserId).commit();
    }

    public void setLoginSession() {
        mEditor.putBoolean(VariableBag.LOGIN, true);
        mEditor.commit();
    }

    public boolean getLoginSession() {
        boolean login = mSharedPreferences.getBoolean(VariableBag.LOGIN, false);
        return login;
    }

    public void setWiFiSession(boolean wiFiSession) {
        mEditor.putBoolean("wifi", wiFiSession).commit();
    }

    public boolean getWiFiSession() {
        boolean login = mSharedPreferences.getBoolean("wifi", false);
        return login;
    }


    public void setKeyValueString(String key, String value) {
        mEditor.putString(key, value).commit();
    }

    public void setKeyValueInt(String key, int value) {
        mEditor.putInt(key, value).commit();
    }

    public void setKeyValueBoolean(String key, boolean value) {
        mEditor.putBoolean(key, value).commit();
    }

    public void setMapKey(String key) {

        mEditor.putString(key, "mapKey").commit();

    }


    public void setFirstTime(boolean key) {

        mEditor.putBoolean("firstTime", key).commit();

    }

    public boolean getFirstTime() {

        boolean val = mSharedPreferences.getBoolean("firstTime", false);
        return val;
    }


    public String getKeyValueString(String key) {
        String getValue = mSharedPreferences.getString(key, " ");
        return getValue;
    }

    public int getKeyValueInt(String key) {
        int getValue = mSharedPreferences.getInt(key, 0);
        return getValue;
    }

    public boolean getKeyValueBoolean(String key) {
        boolean getValue = mSharedPreferences.getBoolean(key, false);
        return getValue;
    }


    public void setPayType(String key) {

        mEditor.putString("payType", key).commit();

    }

    public String getPayType() {

        String val = mSharedPreferences.getString("payType", "");
        return val;
    }


}


