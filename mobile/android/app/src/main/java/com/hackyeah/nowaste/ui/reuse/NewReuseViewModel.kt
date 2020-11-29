package com.hackyeah.nowaste.ui.reuse

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.hackyeah.nowaste.data.remote.NewReuse
import com.hackyeah.nowaste.data.remote.Reuse
import com.hackyeah.nowaste.data.repository.ProductRepository
import com.hackyeah.nowaste.utils.Resource
import okhttp3.MultipartBody

class NewReuseViewModel @ViewModelInject constructor(
    private val repository: ProductRepository
) : ViewModel() {
    private var _files: List<MultipartBody.Part> = listOf()
    private val _newReuse = MutableLiveData<NewReuse>()
    private val _response = _newReuse.switchMap { newReuse ->
        repository.addNewReuse(newReuse, _files)
    }

    //Interface
    val response: LiveData<Resource<Reuse>> = _response
    fun addNewReuse(newReuse: NewReuse, file: List<MultipartBody.Part>) {
        _files = file
        _newReuse.value = newReuse
    }
}