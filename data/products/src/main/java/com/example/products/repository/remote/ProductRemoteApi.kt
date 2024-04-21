package com.example.products.repository.remote

import com.example.products.module.ProductDto

interface ProductRemoteApi {

    suspend fun getProducts(): List<ProductDto>

    suspend fun getProductToById(id: Int): ProductDto

}