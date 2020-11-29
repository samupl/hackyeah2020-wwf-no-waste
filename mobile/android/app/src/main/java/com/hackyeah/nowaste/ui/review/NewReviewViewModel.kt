package com.hackyeah.nowaste.ui.review

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.hackyeah.nowaste.data.remote.NewReview
import com.hackyeah.nowaste.data.remote.ReviewResponse
import com.hackyeah.nowaste.data.repository.ProductRepository
import com.hackyeah.nowaste.utils.Resource

class NewReviewViewModel @ViewModelInject constructor(
    private val repository: ProductRepository
) : ViewModel() {
    private val _newReview = MutableLiveData<NewReview>()
    private val _response = _newReview.switchMap { newReview ->
//        repository.addNewReview(
//            newReview.productId, newReview.boxReusable, newReview.boxRecycable,
//            newReview.boxFromRecycling, newReview.productReusable, newReview.productRecycable,
//            newReview.productFromRecycling, newReview.repairable, newReview.comment
//        )
        repository.addNewReview(newReview)
    }

    //Interface
    val response: LiveData<Resource<ReviewResponse>> = _response
    fun addNewReuse(newReview: NewReview) {
        _newReview.value = newReview
    }
}