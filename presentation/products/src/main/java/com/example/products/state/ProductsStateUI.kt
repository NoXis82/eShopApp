package com.example.products.state

import androidx.compose.runtime.Immutable
import com.example.domain.util.RepositoryError
import com.example.products.model.Product

@Immutable
data class ProductsStateUI(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: RepositoryError? = null,
    val isRefreshing: Boolean = false
)