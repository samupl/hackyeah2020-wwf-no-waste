package com.hackyeah.nowaste.ui.reuse

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.hackyeah.nowaste.data.model.Product
import com.hackyeah.nowaste.data.remote.Reuse
import com.hackyeah.nowaste.data.remote.ReuseData
import com.hackyeah.nowaste.databinding.ReuseListActivityBinding
import com.hackyeah.nowaste.ui.product.adapter.ProductAdapter
import com.hackyeah.nowaste.ui.product.adapter.items.*
import com.hackyeah.nowaste.utils.EXTRA_PRODUCT
import com.hackyeah.nowaste.utils.EXTRA_REUSE
import com.hackyeah.nowaste.utils.Resource
import com.hackyeah.nowaste.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReuseListActivity : AppCompatActivity() {
    private val viewModel: ReuseListViewModel by viewModels()

    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ReuseListActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val product = intent.getSerializableExtra(EXTRA_PRODUCT) as Product
        val items = getItems(product)

        adapter = ProductAdapter().apply {
            setItems(items)
        }
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = adapter

        setupObservers()
        viewModel.getReuseList(product.id)
    }

    private fun getItems(product: Product): ArrayList<Any> {
        val imageItem = ImageItem(product.photoUrl)
        val titleItem = TitleItem(product.name)
        val categoryItem = CategoryItem(product.category)
        val tagsItem = TagsItem(product.tags)
        val addReuse = ActionItem("Add reuse") {
            val intent = Intent(this, NewReuseActivity::class.java).apply {
                putExtra(EXTRA_PRODUCT, product)
            }
            startActivity(intent)
        }
        return arrayListOf(
            imageItem, titleItem,
            categoryItem, tagsItem,
            addReuse
        )
    }

    private fun setupObservers() {
        viewModel.reuseList.observe(this, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    val reuses = getReuseList(it.data)
                    if (reuses.size > 0) {
                        adapter.addItems(reuses)
                    } else {
                        adapter.addItems(arrayListOf(NoDataItem("no reuse propositions")))
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

    private fun getReuseList(data: ReuseData?): ArrayList<Any> {
        val forProduct: List<Any> =
            data?.forProduct?.map { reuse -> ReuseItem(reuse) { r: Reuse -> onReuseClick(r) } }
                ?: listOf()
        val proposals: List<Any> =
            data?.proposals?.map { reuse -> ReuseItem(reuse) { r: Reuse -> onReuseClick(r) } }
                ?: listOf()
        val collection = ArrayList(forProduct)
        collection.addAll(proposals)
        return collection
    }

    private fun onReuseClick(reuse: Reuse) {
        val intent = Intent(this, ReuseDetailsActivity::class.java).apply {
            putExtra(EXTRA_REUSE, reuse)
        }
        startActivity(intent)
    }
}