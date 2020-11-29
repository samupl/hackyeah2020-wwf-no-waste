package com.hackyeah.nowaste.ui.review

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.hackyeah.nowaste.data.model.Review
import com.hackyeah.nowaste.data.repository.ProductRepository
import com.hackyeah.nowaste.utils.Resource

class ReviewsViewModel @ViewModelInject constructor(
    private val repository: ProductRepository
) : ViewModel() {
    private val _id = MutableLiveData<Long>()
    private val _reviews = _id.switchMap { id ->
        repository.getProductReviews(id)
    }

    //Interface
    val reviews: LiveData<Resource<List<Review>>> = _reviews
    fun getReviews(id: Long) {
        _id.value = id
    }
}