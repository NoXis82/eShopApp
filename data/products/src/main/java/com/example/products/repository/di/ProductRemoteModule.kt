package com.example.products.repository.di

import com.example.products.ProductApi
import com.example.products.repository.remote.ProductRemoteApi
import com.example.products.repository.remote.ProductRemoteImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ProductRemoteModule {

    @Singleton
    @Provides
    fun provideListApi(retrofit: Retrofit): ProductApi = retrofit.create(ProductApi::class.java)

    @Singleton
    @Provides
    internal fun provideInitialProductRemote(productApi: ProductApi): ProductRemoteApi {
        return ProductRemoteImpl(productApi)
    }

}