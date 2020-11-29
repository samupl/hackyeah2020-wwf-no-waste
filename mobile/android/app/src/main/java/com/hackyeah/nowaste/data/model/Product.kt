package com.hackyeah.nowaste.data.model

import java.io.Serializable

class Product(
    val id: Long,
    val barcode: String,
    val name: String,
    val photoUrl: String?,
    val averageRatings: Rating,
    val category: Category,
    val tags: List<Tag>
) : Serializable
