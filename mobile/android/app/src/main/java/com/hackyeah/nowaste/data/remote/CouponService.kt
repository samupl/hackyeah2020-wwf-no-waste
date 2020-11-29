package com.hackyeah.nowaste.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CouponService {
    @GET("user-coupons/{user}")
    suspend fun getCoupons(@Path("user") user: String): Response<List<Coupon>>
}

class Coupon(
    val id: String,
    val required_opinions: Long,
    val description: String,
    val coupon: String,
    val expiry_date: String,
    val icon: String
)