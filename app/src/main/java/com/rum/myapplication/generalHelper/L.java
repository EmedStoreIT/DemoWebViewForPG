package com.rum.myapplication.generalHelper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.rum.myapplication.BuildConfig;

/**
 * Created by asama on 06-04-2017.
 */

public class L {
    private static final String MY_LOG_TAG = "@***@***@";

    public static void showError(String string) {
        if (BuildConfig.DEBUG) {
            Log.e(MY_LOG_TAG, string);
        }
    }

    public static void showToast(Context context, String string) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }
}
