package com.rum.myapplication.gif

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.rum.myapplication.R
import com.rum.myapplication.databinding.ActivityGifBinding

class GifActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGifBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGifBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setSupportActionBar(binding.toolbar)

        loadGIFTest()
    }

    private fun loadGIFTest() {
        Glide.with(this)
            .load(R.raw.gif_1)
            .into(binding.imageView);
    }
}