package com.hackyeah.nowaste.ui

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.hackyeah.nowaste.R
import com.hackyeah.nowaste.utils.EXTRA_NAME
import com.hackyeah.nowaste.databinding.WelcomeActivityBinding
import com.hackyeah.nowaste.ui.main.MainActivity
import com.hackyeah.nowaste.utils.Session
import com.hackyeah.nowaste.utils.showToast

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = WelcomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nameInput.setImeOptions(EditorInfo.IME_ACTION_DONE)
        binding.nameInput.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    binding.nextButton.performClick()
                    return false
                }
                return true
            }

        })
        binding.nextButton.setOnClickListener {
            if (binding.nameInput.text.isNullOrEmpty()) {
                showToast(R.string.warning_no_author)
            } else {
                Session.author = binding.nameInput.text.toString()
                Session.productsBaseUrl = binding.productBaseUrlInput.text.toString()
                Session.couponBaseUrl = binding.couponBaseUrlInput.text.toString()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        binding.productBaseUrlInput.setText(Session.productsBaseUrl)
        binding.couponBaseUrlInput.setText(Session.couponBaseUrl)
    }
}