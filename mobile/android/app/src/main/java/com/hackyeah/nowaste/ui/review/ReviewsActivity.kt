package com.hackyeah.nowaste.ui.review

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.hackyeah.nowaste.data.model.Product
import com.hackyeah.nowaste.data.model.Review
import com.hackyeah.nowaste.databinding.ReviewsActivityBinding
import com.hackyeah.nowaste.ui.product.adapter.ProductAdapter
import com.hackyeah.nowaste.ui.product.adapter.items.*
import com.hackyeah.nowaste.utils.EXTRA_PRODUCT
import com.hackyeah.nowaste.utils.Resource
import com.hackyeah.nowaste.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewsActivity : AppCompatActivity() {
    private val viewModel: ReviewsViewModel by viewModels()

    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ReviewsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val product = intent.getSerializableExtra(EXTRA_PRODUCT) as Product
        val items = getItems(product)

        adapter = ProductAdapter().apply {
            setItems(items)
        }
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = adapter

        setupObservers()
        viewModel.getReviews(product.id)
    }

    private fun getItems(product: Product): ArrayList<Any> {
        val imageItem = ImageItem(product.photoUrl)
        val titleItem = TitleItem(product.name)
        val categoryItem = CategoryItem(product.category)
        val tagsItem = TagsItem(product.tags)
        val addReview = ActionItem("Add review") {
            val intent = Intent(this, NewReviewActivity::class.java).apply {
                putExtra(EXTRA_PRODUCT, product)
            }
            startActivity(intent)
        }
        return arrayListOf(
            imageItem, titleItem,
            categoryItem, tagsItem,
            addReview
        )
    }

    private fun setupObservers() {
        viewModel.reviews.observe(this, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    val reviews = getReviews(it.data)
                    if (reviews.size > 0) {
                        adapter.addItems(reviews)
                    } else {
                        adapter.addItems(arrayListOf(NoDataItem("no reviews")))
                    }
                }
                Resource.Status.ERROR -> {
                    it.message?.let { message -> showToast(message) }
                }
                Resource.Status.LOADING -> {
                }
            }
        })
    }

    private fun getReviews(data: List<Review>?): ArrayList<Any> {
        val collection = data?.map { review -> ReviewItem(review) } ?: listOf()
        return ArrayList(collection)
    }
}