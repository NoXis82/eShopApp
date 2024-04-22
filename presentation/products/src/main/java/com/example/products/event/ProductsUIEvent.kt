package com.example.products.event

import com.example.products.model.Product

sealed class ProductsUIEvent {

    data object Dismiss : ProductsUIEvent()

    data object GetProductList: ProductsUIEvent()

    data class ProductClicked(val item: Product) : ProductsUIEvent()
}
