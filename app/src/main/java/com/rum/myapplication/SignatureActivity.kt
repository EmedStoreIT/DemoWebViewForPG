package com.rum.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rum.myapplication.databinding.ActivitySignatureBinding

class SignatureActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignatureBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignatureBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}