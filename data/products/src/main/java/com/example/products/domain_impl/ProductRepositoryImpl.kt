package com.example.products.domain_impl

import com.example.domain.util.RepositoryError
import com.example.domain.util.RequestResult
import com.example.products.model.Product
import com.example.products.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(

) : ProductRepository {
    override fun getProducts(): Flow<RequestResult<List<Product>, RepositoryError>> {
        TODO("Not yet implemented")
    }

    override fun getProductToById(id: Int): Flow<RequestResult<Product, RepositoryError>> {
        TODO("Not yet implemented")
    }
}