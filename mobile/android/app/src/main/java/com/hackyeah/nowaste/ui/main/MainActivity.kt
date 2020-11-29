package com.hackyeah.nowaste.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.zxing.BarcodeFormat
import com.google.zxing.ResultPoint
import com.hackyeah.nowaste.R
import com.hackyeah.nowaste.databinding.MainActivityBinding
import com.hackyeah.nowaste.ui.coupon.CouponsActivity
import com.hackyeah.nowaste.ui.product.ProductActivity
import com.hackyeah.nowaste.utils.*
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val CAMERA_PERMISSION_REQUEST: Int = 1234

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: MainActivityBinding
    private var processing: Boolean = false

    private val decodeCallback = object : BarcodeCallback {
        override fun barcodeResult(result: BarcodeResult?) {
            result?.let {
                if (it.barcodeFormat == BarcodeFormat.EAN_13 ||
                    it.barcodeFormat == BarcodeFormat.EAN_8
                ) {
                    if (!processing) {
                        processing = true
                        getProductReview(it.text)
                    }
                }
            }
        }

        override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.name.setText(getString(R.string.main_hello, Session.author))
        binding.barcodeInput.setImeOptions(EditorInfo.IME_ACTION_DONE)
        binding.barcodeInput.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    binding.nextButton.performClick()
                    return false
                }
                return true
            }

        })
        binding.nextButton.setOnClickListener {
            if (!binding.barcodeInput.text.isNullOrEmpty()) {
                getProductReview(binding.barcodeInput.text.toString())
            }
        }
        binding.couponsButton.setOnClickListener {
            val intent = Intent(this, CouponsActivity::class.java)
            startActivity(intent)
        }

        setupObservers()
        setupScanner()
        checkCameraPermission()
    }

    override fun onResume() {
        super.onResume()

        processing = false
        binding.scanner.resume()
    }

    override fun onPause() {
        super.onPause()

        binding.scanner.pause()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            CAMERA_PERMISSION_REQUEST -> {
                if ((grantResults.isNotEmpty() && grantResults[0] !=
                            PackageManager.PERMISSION_GRANTED)
                ) {
                    showToast(R.string.error_missing_camera_permission)
                }
            }
        }
    }

    //Internal methods
    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST
            )
        }
    }

    private fun setupScanner() {
        val formats = listOf(BarcodeFormat.QR_CODE, BarcodeFormat.EAN_13, BarcodeFormat.EAN_8)
        binding.scanner.decoderFactory = DefaultDecoderFactory(formats)
        binding.scanner.decodeContinuous(decodeCallback)
    }

    private fun getProductReview(barcode: String) {
        viewModel.getProduct(barcode)
    }

    private fun setupObservers() {
        viewModel.product.observe(this, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressView.visibility = View.GONE
                    val intent = Intent(this, ProductActivity::class.java).apply {
                        putExtra(EXTRA_PRODUCT, it.data)
                    }
                    startActivity(intent)
                    processing = false
                }
                Resource.Status.ERROR -> {
                    binding.progressView.visibility = View.GONE
                    it.message?.let { message -> showToast(message) }
                    processing = false
                }
                Resource.Status.LOADING -> {
                    binding.progressView.visibility = View.VISIBLE
                    processing = false
                }
            }
        })
    }
}