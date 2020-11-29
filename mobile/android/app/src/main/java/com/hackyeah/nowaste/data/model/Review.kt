package com.hackyeah.nowaste.data.model

import java.math.BigDecimal

class Review(
    val id: Long,
    val date: List<Int>,
    val rating: Rating,
    val comment: String,
    val author: String
)