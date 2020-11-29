package com.hackyeah.nowaste.data.model

import java.io.Serializable
import java.math.BigDecimal

class Rating(
    val boxReusable: BigDecimal?,
    val boxRecycable: BigDecimal?,
    val boxFromRecycling : BigDecimal?,
    val productReusable: BigDecimal?,
    val productRecycable: BigDecimal?,
    val productFromRecycling: BigDecimal?,
    val repairable: BigDecimal?
) : Serializable