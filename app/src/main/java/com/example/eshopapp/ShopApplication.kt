package com.example.eshopapp

import android.app.Application
import com.example.products.repository.ProductRepository
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import javax.inject.Provider

@HiltAndroidApp
class ShopApplication: Application()