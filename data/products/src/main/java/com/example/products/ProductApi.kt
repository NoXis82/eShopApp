package com.example.products

import com.example.products.module.ProductDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi {

    @GET("products")
    suspend fun getProducts(): List<ProductDto>

    @GET("products/{id}")
    suspend fun getProductToById(
        @Path("Id") productId: Int
    ): ProductDto

}