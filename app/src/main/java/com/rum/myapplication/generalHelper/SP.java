package com.rum.myapplication.generalHelper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by nectarbits on 15/02/16.
 */
public class SP {

    public static final String SP_TRUE = "SP_TRUE";
    public static final String SP_FALSE = "SP_FALSE";
    public static final String LOGIN_STATUS = "LOGIN_STATUS";
    public static final String USER_FIRST_NAME = "USER_FIRST_NAME";
    public static final String USER_LAST_NAME = "USER_LAST_NAME";
    public static final String USER_MOBILE = "USER_MOBILE";
    public static final String USER_EMAIL = "USER_EMAIL";
    public static final String USER_ID = "USER_ID";
    public static final String USER_LAT = "USER_LAT";
    public static final String USER_LONG = "USER_LONG";
    public static final String SIGNATURE_IMAGE_PATH = "SIGNATURE_IMAGE_PATH";
    public static final String ON_DUTY = "ON_DUTY";

    /**
     * @param mContext
     * @param key
     * @param value
     */
    public static void savePreferences(Context mContext, String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value).apply();
    }


    /**
     * @param context
     * @param keyValue
     * @return
     */
    public static String getPreferences(Context context, String keyValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(keyValue, "");
    }

    /**
     * @param mContext
     */
    public static void removeAllSharedPreferences(Context mContext) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().apply();
    }
}
