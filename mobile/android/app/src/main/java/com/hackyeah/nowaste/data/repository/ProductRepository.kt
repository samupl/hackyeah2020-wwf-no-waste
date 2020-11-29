package com.hackyeah.nowaste.data.repository

import com.hackyeah.nowaste.data.remote.NewReuse
import com.hackyeah.nowaste.data.remote.NewReview
import com.hackyeah.nowaste.data.remote.ProductRemoteDataSource
import com.hackyeah.nowaste.utils.performGetOperation
import okhttp3.MultipartBody
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val remoteDataSource: ProductRemoteDataSource
) {

    fun getProduct(barcode: String) = performGetOperation(
        networkCall = { remoteDataSource.getProduct(barcode) }
    )

    fun getProductReviews(productId: Long) = performGetOperation(
        networkCall = { remoteDataSource.getProductReviews(productId) }
    )

    fun getProductReuseList(productId: Long) = performGetOperation(
        networkCall = { remoteDataSource.getProductReuseList(productId) }
    )

    fun addNewReuse(newReuse: NewReuse, files: List<MultipartBody.Part>) = performGetOperation(
        networkCall = { remoteDataSource.addReuse(newReuse, files) }
    )

    fun addNewReview(newReview: NewReview) = performGetOperation(
        networkCall = { remoteDataSource.addReview(newReview) }
    )
}