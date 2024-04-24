package com.example.products.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.presentation.LoadingComponent
import com.example.products.event.ProductsUIEvent
import com.example.products.viewmodel.ProductsViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(
    viewModel: ProductsViewModel = hiltViewModel()
) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val selectedItem = remember { mutableStateOf(0) }
    val coroutineScope = rememberCoroutineScope()

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
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet {
                        Text(
                            "Drawer title",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .background(Color.Red)
                                .wrapContentHeight(Alignment.CenterVertically)
                                .wrapContentWidth(Alignment.CenterHorizontally),
                            fontSize = 26.sp,
                            color = Color.White

                        )
                        HorizontalDivider()
                        NavigationDrawerItem(
                            label = { Text(text = "Drawer Item 1") },
                            selected = selectedItem.value == 1,
                            shape = RoundedCornerShape(0.dp),
                            icon = {
                                Icon(imageVector = Icons.Filled.Call, contentDescription = null)
                            },
                            onClick = {
                                coroutineScope.launch {
                                    selectedItem.value = 1
                                    drawerState.close()
                                }
                            }
                        )
                    }
                }
            ) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "AppBar") },
                            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Gray),
                            navigationIcon = {
                                IconButton(onClick = {
                                    coroutineScope.launch {
                                        drawerState.apply {
                                            if (isClosed) open() else close()
                                        }
                                    }
                                }) {
                                    Icon(imageVector = Icons.Filled.Menu, contentDescription = "menu")
                                }
                            }
                        )
                    }
                ) { paddingValues ->
                    SwipeRefresh(
                        state = swipeRefreshState,
                        onRefresh = {
                            viewModel.onUIEvent(ProductsUIEvent.Refresh)
                        }
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
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
    }
}