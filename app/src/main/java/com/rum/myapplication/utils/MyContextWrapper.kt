package com.rum.myapplication.utils

import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.os.LocaleList
import java.util.*

class MyContextWrapper(base: Context?) : ContextWrapper(base) {

    fun wrap(context: Context): MyContextWrapper {
        var mContext = context
        val res = mContext.resources
        val configuration = res.configuration
        val newLocale = Locale(AppUtil.getSavedLanguage(mContext)!!)
        when {
            Build.VERSION.SDK_INT >= 24 -> { // API at-least 24
                configuration.setLocale(newLocale)
                val localeList = LocaleList(newLocale)
                LocaleList.setDefault(localeList)
                configuration.setLocales(localeList)
                mContext = mContext.createConfigurationContext(configuration)
            }
            Build.VERSION.SDK_INT >= 17 -> { // API at-least 17
                configuration.setLocale(newLocale)
                mContext = mContext.createConfigurationContext(configuration)
            }
            else -> {
                configuration.setLocale(newLocale)
                res.updateConfiguration(configuration, res.displayMetrics)

            }
        }
        return MyContextWrapper(mContext)
    }
}