package com.example.products.module

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RatingDto(
    @SerialName("rate") val rate: Double,
    @SerialName("count") val count: Int,
)
