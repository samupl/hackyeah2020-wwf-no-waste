package com.hackyeah.nowaste.data.repository

import com.hackyeah.nowaste.data.remote.CouponRemoteDataSource
import com.hackyeah.nowaste.utils.performGetOperation
import javax.inject.Inject

class CouponRepository @Inject constructor(
    private val remoteDataSource: CouponRemoteDataSource
) {

    fun getCoupons(author: String) = performGetOperation(
        networkCall = { remoteDataSource.getCoupons(author) }
    )
}