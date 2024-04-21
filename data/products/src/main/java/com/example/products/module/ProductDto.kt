package com.example.products.module

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("price") val price: Double,
    @SerialName("description") val description: String,
    @SerialName("category") val category: String,
    @SerialName("image") val image: String,
    @SerialName("rating") val ratingDto: RatingDto,
)