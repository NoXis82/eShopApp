package com.example.products.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.products.viewmodel.ProductsViewModel

@Composable
fun ProductsScreen() {
    val viewModel: ProductsViewModel = hiltViewModel()
    val products by viewModel.products.collectAsState(emptyList())

    Text(
        text = "$products",
        modifier = Modifier.fillMaxSize()
    )
}