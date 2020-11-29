package com.hackyeah.nowaste.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.hackyeah.nowaste.data.model.Product
import com.hackyeah.nowaste.data.repository.ProductRepository
import com.hackyeah.nowaste.utils.Resource

class MainViewModel @ViewModelInject constructor(
    private val repository: ProductRepository
) : ViewModel(){

    private val _barcode = MutableLiveData<String>()
    private val _product = _barcode.switchMap { barcode ->
        repository.getProduct(barcode)
    }

    //Interface
    val product: LiveData<Resource<Product>> = _product
    fun getProduct(barcode: String) {
        _barcode.value = barcode
    }
}