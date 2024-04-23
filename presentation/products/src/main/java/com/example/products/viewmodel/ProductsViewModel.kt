package com.example.products.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.domain.util.RequestResult
import com.example.presentation.StateAndEventViewModel
import com.example.products.event.ProductsUIEvent
import com.example.products.repository.ProductRepository
import com.example.products.state.ProductsStateUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productRepository: Provider<ProductRepository>
) : StateAndEventViewModel<ProductsStateUI, ProductsUIEvent>(ProductsStateUI()) {


    override suspend fun handleEvent(uiEvent: ProductsUIEvent) {
        when (uiEvent) {
            is ProductsUIEvent.Dismiss -> {
                //todo add navigate to back
            }

            is ProductsUIEvent.GetProductList -> {
                getProductsListings()
            }

            is ProductsUIEvent.ProductClicked -> {
                //tod add navigate to product details
            }

            is ProductsUIEvent.Refresh -> {
                getProductsListings()
            }
        }
    }


    private fun getProductsListings() {
        viewModelScope.launch {
            productRepository.get().getProducts()
                .onStart {
                    updateUIState { copy(isLoading = true) }
                }
                .collect { result ->
                    when (result) {
                        is RequestResult.Error -> {
                            updateUIState {
                                copy(isLoading = false, error = result.error)
                            }
                        }

                        is RequestResult.Success -> {
                            updateUIState {
                                copy(
                                    products = result.data,
                                    isLoading = false
                                )
                            }
                        }
                    }
                }
        }
    }


}