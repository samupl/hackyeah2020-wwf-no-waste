package com.hackyeah.nowaste.ui.coupon

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.hackyeah.nowaste.data.remote.Coupon
import com.hackyeah.nowaste.data.repository.CouponRepository
import com.hackyeah.nowaste.utils.Resource

class CouponsViewModel @ViewModelInject constructor(
    private val repository: CouponRepository
) : ViewModel() {
    private val _author = MutableLiveData<String>()
    private val _coupons = _author.switchMap { author ->
        repository.getCoupons(author)
    }

    //Interface
    val coupons: LiveData<Resource<List<Coupon>>> = _coupons
    fun getCoupons(author: String) {
        _author.value = author
    }
}