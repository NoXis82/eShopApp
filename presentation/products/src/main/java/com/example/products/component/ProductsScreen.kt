package com.example.products.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.presentation.LoadingComponent
import com.example.products.event.ProductsUIEvent
import com.example.products.viewmodel.ProductsViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun ProductsScreen(
    viewModel: ProductsViewModel = hiltViewModel()
) {
    val stateUI by viewModel.uiState.collectAsState()

    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = stateUI.isRefreshing
    )
    LaunchedEffect(true) {
        viewModel.onUIEvent(ProductsUIEvent.GetProductList)
    }


    when {
        stateUI.isLoading -> LoadingComponent()
        stateUI.error != null -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(text = stateUI.error.toString())
            }
        }

        stateUI.products.isNotEmpty() -> {
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                    viewModel.onUIEvent(ProductsUIEvent.Refresh)
                }
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(stateUI.products.size) { i ->
                        val item = stateUI.products[i]
                        ProductItem(
                            product = item,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { }
                                .padding(16.dp)
                        )
                        if (i < stateUI.products.size) {
                            HorizontalDivider(
                                modifier = Modifier.padding(
                                    horizontal = 16.dp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}