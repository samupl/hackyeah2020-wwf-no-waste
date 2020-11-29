package com.hackyeah.nowaste.ui.coupon

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.hackyeah.nowaste.R
import com.hackyeah.nowaste.data.model.Product
import com.hackyeah.nowaste.data.model.Review
import com.hackyeah.nowaste.data.remote.Coupon
import com.hackyeah.nowaste.databinding.CouponsActivityBinding
import com.hackyeah.nowaste.ui.product.adapter.ProductAdapter
import com.hackyeah.nowaste.ui.product.adapter.items.*
import com.hackyeah.nowaste.ui.reuse.ReuseListActivity
import com.hackyeah.nowaste.ui.review.ReviewsActivity
import com.hackyeah.nowaste.utils.EXTRA_PRODUCT
import com.hackyeah.nowaste.utils.Resource
import com.hackyeah.nowaste.utils.Session
import com.hackyeah.nowaste.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CouponsActivity : AppCompatActivity() {
    private val viewModel: CouponsViewModel by viewModels()
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = CouponsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val items = getItems()
        adapter = ProductAdapter().apply {
            setItems(items)
        }
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = adapter

        setupObservers()
        getCoupons()
    }

    private fun getItems(): ArrayList<Any> {
        val titleItem = TitleItem(getString(R.string.coupons_title))
        return arrayListOf(titleItem)
    }

    private fun setupObservers() {
        viewModel.coupons.observe(this, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    val coupons = getCouponsItems(it.data)
                    if (coupons.size > 0) {
                        adapter.addItems(coupons)
                    } else {
                        adapter.addItems(arrayListOf(
                            NoDataItem(getString(R.string.coupons_no_coupons))))
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

    private fun getCoupons() {
        viewModel.getCoupons(Session.author)
    }

    private fun getCouponsItems(data: List<Coupon>?): ArrayList<Any> {
        val collection = data?.map { coupon -> CouponItem(coupon) } ?: listOf()
        return ArrayList(collection)
    }
}