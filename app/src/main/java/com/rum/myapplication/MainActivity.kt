package com.rum.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rum.myapplication.databinding.ActivityMainBinding
import com.rum.myapplication.gif.GifActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.btnLoadURL.setOnClickListener {
            navigateToWebViewPage()
        }

        binding.btnLoadGIF.setOnClickListener {
            navigateToGifPage()
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
}