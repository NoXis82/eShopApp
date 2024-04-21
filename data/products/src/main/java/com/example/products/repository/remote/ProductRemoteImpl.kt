package com.example.products.repository.remote

import com.example.products.ProductApi
import com.example.products.module.ProductDto
import javax.inject.Inject

class ProductRemoteImpl @Inject constructor(
    private val productApi: ProductApi
): ProductRemoteApi {
    override suspend fun getProducts(): List<ProductDto> {
        return productApi.getProducts()
    }

    override suspend fun getProductToById(id: Int): ProductDto {
        return productApi.getProductToById(id)
    }

}