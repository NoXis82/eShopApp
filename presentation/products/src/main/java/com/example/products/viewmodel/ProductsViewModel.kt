package com.example.products.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.util.RequestResult
import com.example.products.model.Product
import com.example.products.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _products = MutableStateFlow(emptyList<Product>())
    val products get() = _products.asStateFlow()

    init {
        getProductsListings()
    }

    private fun getProductsListings() {
        viewModelScope.launch {
            productRepository.getProducts().collect { result ->
                when (result) {
                    is RequestResult.Error -> {

                    }

                    is RequestResult.Success -> {
                        _products.value = result.data
                    }
                }

            }
        }
    }

}