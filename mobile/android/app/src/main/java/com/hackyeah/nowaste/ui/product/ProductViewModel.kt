package com.hackyeah.nowaste.ui.product

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.hackyeah.nowaste.data.repository.ProductRepository

class ProductViewModel @ViewModelInject constructor(
    private val repository: ProductRepository
) : ViewModel()