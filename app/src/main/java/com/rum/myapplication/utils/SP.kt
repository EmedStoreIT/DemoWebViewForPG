package com.rum.myapplication.utils

import android.content.Context
import android.preference.PreferenceManager

object SP {

    const val SELECTED_LANGUAGE = "SELECTED_LANGUAGE"
    const val LANGUAGE_ENGLISH = "en"
    const val LANGUAGE_HINDI = "hi"
    const val LANGUAGE_RUSSIAN = "ru"
    const val LANGUAGE_UZBEK = "uz"
}

fun Context.getPreferences(keyValue: String): String? {
    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
    return sharedPreferences.getString(keyValue, "")
}

fun Context.removeAllSharedPreferences() {
    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
    val editor = sharedPreferences.edit()
    editor.clear().apply()
}

fun Context.savePreferences(key: String, value: String?) {
    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
    val editor = sharedPreferences.edit()
    editor.putString(key, value).apply()
}