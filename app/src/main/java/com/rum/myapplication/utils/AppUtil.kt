package com.rum.myapplication.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.rum.myapplication.R

object AppUtil {
    fun changeLocalization(activity: Activity, position: Int) {
        var languagetTemp = ""
        when (position) {
            0 -> {
                languagetTemp = SP.LANGUAGE_ENGLISH
            }
            1 -> {
                languagetTemp = SP.LANGUAGE_HINDI
            }
            2 -> {
                languagetTemp = SP.LANGUAGE_RUSSIAN
            }
            3 -> {
                languagetTemp = SP.LANGUAGE_UZBEK
            }
        }

        languagetTemp.savePref(activity, SP.SELECTED_LANGUAGE)
        val intent = Intent(activity.getString(R.string.broadcast_language_changed))
        LocalBroadcastManager.getInstance(activity).sendBroadcast(intent)
        activity.recreate()
    }

    fun getSavedLanguage(context: Context): String? {
        val savedLanguage: String = context.getPref(SP.SELECTED_LANGUAGE)
        return if (!TextUtils.isEmpty(savedLanguage)) savedLanguage else SP.LANGUAGE_ENGLISH
    }
}

fun Context.getPref(key: String): String {
    val prefValue = this.getPreferences(key)
    return checkNullAndGetValue(prefValue)
}

fun String.savePref(mContext: Context, key: String) {
    mContext.savePreferences(key, if (!TextUtils.isEmpty(this)) this else "")
}

fun checkNullAndGetValue(stringValue: String?): String {
    return if (stringValue != null && !TextUtils.isEmpty(stringValue)) stringValue else ""
}