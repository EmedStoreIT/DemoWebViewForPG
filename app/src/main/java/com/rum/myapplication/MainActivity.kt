package com.rum.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.TelephonyManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rum.myapplication.databinding.ActivityMainBinding
import com.rum.myapplication.fblogin.FBLoginActivity
import com.rum.myapplication.gif.GifActivity


class MainActivity : AppCompatActivity() {

    private lateinit var mContext: Context
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContext = this

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.btnLoadURL.setOnClickListener {
            navigateToWebViewPage()
        }

        binding.btnLoadGIF.setOnClickListener {
            navigateToGifPage()
        }

        binding.btnGetCountryName.setOnClickListener {
            checkCountryName()
        }

        binding.btnLoadGIF.setOnClickListener {
            navigateToGifPage()
        }

        binding.btnFBLogin.setOnClickListener {
            startActivity(Intent(mContext, FBLoginActivity::class.java))
        }
    }

    private fun navigateToWebViewPage() {
        startActivity(
            Intent(this, WebViewActivity::class.java)
        )
    }

    private fun navigateToGifPage() {
        startActivity(
            Intent(this, GifActivity::class.java)
        )
    }

    private fun checkCountryName() {
        try {
//        val country = applicationContext.resources.configuration.locale.displayCountry
            val tm = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
            val countryCode = tm.simCountryIso

            Toast.makeText(mContext, "Country Code = $countryCode", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(mContext, e.message, Toast.LENGTH_SHORT).show()
        }
    }
}