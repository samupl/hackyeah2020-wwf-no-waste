package com.hackyeah.nowaste.data.remote

import com.hackyeah.nowaste.data.model.Category
import com.hackyeah.nowaste.data.model.Product
import com.hackyeah.nowaste.data.model.Review
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*
import java.io.Serializable

interface ProductService {
    @GET("product/find-by-barcode/{barcode}")
    suspend fun getProduct(@Path("barcode") barcode: String): Response<Product>

    @GET("review/find-by-product/{id}")
    suspend fun getProductReviews(@Path("id") id: Long): Response<List<Review>>

    @GET("reusage/find-by-product/{id}")
    suspend fun getProductReuseList(@Path("id") id: Long): Response<ReuseData>

    @Multipart
    @PUT("reusage")
    suspend fun addReuse(
        @Part("reusage") reuse: NewReuse,
        @Part photos: List<MultipartBody.Part>

    ): Response<Reuse>

    @PUT("review")
    suspend fun addReview(@Body review: NewReview): Response<ReviewResponse>
}

class Reuse(
    val id: Long,
    val date: List<Int>,
    val title: String,
    val author: String,
    val description: String,
    val upVotes: Int,
    val downVotes: Int,
    val photosUrl: List<String>,
    val categories: List<Category>,
    val tags: List<com.hackyeah.nowaste.data.model.Tag>
) : Serializable {
}

class ReviewResponse(val id: Long) : Serializable {
}

class NewReview(
    val author: String,
    val productId: Long, val boxReusable: Int,
    val boxRecycable: Int, val boxFromRecycling: Int,
    val productReusable: Int, val productRecycable: Int,
    val productFromRecycling: Int, val repairable: Int,
    val comment: String
)

class NewReuse(
    val author: String,
    val title: String,
    val description: String,
    val productId: Long,
    val categories: List<Long>,
    val tags: List<Long>
)

class ReuseData(
    val forProduct: List<Reuse>,
    val proposals: List<Reuse>
)
