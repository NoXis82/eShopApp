package com.example.products.repository

import com.example.domain.util.RepositoryError
import com.example.domain.util.RequestResult
import com.example.products.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getProducts(): Flow<RequestResult<List<Product>, RepositoryError>>
}