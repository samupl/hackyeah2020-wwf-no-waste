package com.hackyeah.nowaste.data.remote

import javax.inject.Inject

class CouponRemoteDataSource @Inject constructor(
    private val couponService: CouponService
) : BaseDataSource() {
    suspend fun getCoupons(author: String) =
        getResult { couponService.getCoupons(author) }
}