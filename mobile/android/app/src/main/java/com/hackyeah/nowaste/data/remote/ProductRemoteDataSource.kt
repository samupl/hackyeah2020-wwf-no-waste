package com.hackyeah.nowaste.data.remote

import okhttp3.MultipartBody
import retrofit2.http.Query
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(
    private val productService: ProductService
) : BaseDataSource() {
    suspend fun getProduct(barcode: String) =
        getResult { productService.getProduct(barcode) }

    suspend fun getProductReviews(id: Long) =
        getResult { productService.getProductReviews(id) }

    suspend fun getProductReuseList(id: Long) =
        getResult { productService.getProductReuseList(id) }

    suspend fun addReuse(newReuse: NewReuse, files: List<MultipartBody.Part>) =
        getResult { productService.addReuse(newReuse, files) }

    suspend fun addReview(review: NewReview) =
        getResult { productService.addReview(review) }
}