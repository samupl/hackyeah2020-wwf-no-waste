package com.hackyeah.nowaste.ui.reuse

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.hackyeah.nowaste.data.remote.ReuseData
import com.hackyeah.nowaste.data.repository.ProductRepository
import com.hackyeah.nowaste.utils.Resource

class ReuseListViewModel @ViewModelInject constructor(
    private val repository: ProductRepository
) : ViewModel() {
    private val _id = MutableLiveData<Long>()
    private val _reuseList = _id.switchMap { id ->
        repository.getProductReuseList(id)
    }

    //Interface
    val reuseList: LiveData<Resource<ReuseData>> = _reuseList
    fun getReuseList(id: Long) {
        _id.value = id
    }
}