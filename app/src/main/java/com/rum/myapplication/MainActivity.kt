package com.rum.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rum.myapplication.databinding.ActivityMainBinding
import com.videowithpaint.MainLibraryActivity


class MainActivity : AppCompatActivity() {

    private lateinit var mContext: Context
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContext = this

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        setListeners()
    }

    private fun setListeners() {
        binding.btnNavigateToSign.setOnClickListener {
            startActivity(Intent(mContext, MainLibraryActivity::class.java))
        }
    }
}