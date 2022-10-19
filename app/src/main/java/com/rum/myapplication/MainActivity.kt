package com.rum.myapplication

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rum.myapplication.databinding.ActivityMainBinding
import com.rum.myapplication.utils.AppUtil
import com.rum.myapplication.utils.MyContextWrapper
import com.rum.myapplication.utils.SP
import com.rum.myapplication.utils.getPref


class MainActivity : AppCompatActivity() {

    private lateinit var mContext: Context
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContext = this

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.btnTestLocalization.setOnClickListener {
            showLanguageSelectionDialog(this@MainActivity)
        }
    }

    override fun attachBaseContext(newBase: Context) {
//        val context: Context = MyContextWrapper.wrap(newBase)
        val context = MyContextWrapper(newBase).wrap(newBase)
        super.attachBaseContext(context)
    }

    fun showLanguageSelectionDialog(mContext: Activity) {
        val languageOptions = arrayOf(
            mContext.getString(R.string.language_english),
            mContext.getString(R.string.language_hindi),
            mContext.getString(R.string.language_russian),
            mContext.getString(R.string.language_uzbek)
        )
        val savedLanguage: String = mContext.getPref(SP.SELECTED_LANGUAGE)

        val localization = { dialog: DialogInterface, which: Int ->
            AppUtil.changeLocalization(mContext, which)
            dialog.dismiss()
        }
        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(mContext)
        alertDialogBuilder.setIcon(R.mipmap.logo)
        alertDialogBuilder.setTitle(mContext.getString(R.string.change_localization))
        var index = 0
        if (savedLanguage.equals(SP.LANGUAGE_ENGLISH, true)) {
            index = 0
        } else if (savedLanguage.equals(SP.LANGUAGE_HINDI, true)) {
            index = 1
        } else if (savedLanguage.equals(SP.LANGUAGE_RUSSIAN, true)) {
            index = 2
        }else if (savedLanguage.equals(SP.LANGUAGE_UZBEK, true)) {
            index = 3
        }
        alertDialogBuilder.setSingleChoiceItems(
            languageOptions,
            index,
            DialogInterface.OnClickListener(function = localization)
        )
        val alert: AlertDialog = alertDialogBuilder.create()
        alert.show()

    }
}