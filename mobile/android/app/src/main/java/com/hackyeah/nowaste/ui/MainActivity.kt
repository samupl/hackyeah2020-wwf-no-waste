package com.hackyeah.nowaste.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hackyeah.nowaste.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}