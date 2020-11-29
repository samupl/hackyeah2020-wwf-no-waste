package com.hackyeah.nowaste.ui.review

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.hackyeah.nowaste.data.model.Product
import com.hackyeah.nowaste.data.remote.NewReview
import com.hackyeah.nowaste.databinding.NewReviewActivityBinding
import com.hackyeah.nowaste.utils.EXTRA_PRODUCT
import com.hackyeah.nowaste.utils.Resource
import com.hackyeah.nowaste.utils.Session
import com.hackyeah.nowaste.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewReviewActivity: AppCompatActivity() {
    private val viewModel: NewReviewViewModel by viewModels()
    private lateinit var binding: NewReviewActivityBinding
    private lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = NewReviewActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        product = intent.getSerializableExtra(EXTRA_PRODUCT) as Product

        binding.title.setText(product.name)
        binding.addButton.setOnClickListener {
            addReview()
        }

        setupObservers()
    }

    private fun addReview() {
        val model = NewReview(
            Session.author,
            product.id,
            binding.ratingBoxReusable.rating.toInt(),
            binding.ratingBoxRecycable.rating.toInt(),
            binding.ratingBoxFromRecycling.rating.toInt(),
            binding.ratingProductReusable.rating.toInt(),
            binding.ratingProductRecycable.rating.toInt(),
            binding.ratingProductFromRecycling.rating.toInt(),
            binding.ratingRepairable.rating.toInt(),
            binding.commentInput.text.toString()
        )
        viewModel.addNewReuse(model)
    }

    private fun setupObservers() {
        viewModel.response.observe(this, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressView.visibility = View.GONE
                    finish()
                }
                Resource.Status.ERROR -> {
                    it.message?.let { message -> showToast(message) }
                    binding.progressView.visibility = View.GONE
                }
                Resource.Status.LOADING -> {
                    binding.progressView.visibility = View.VISIBLE
                }
            }
        })
    }
}