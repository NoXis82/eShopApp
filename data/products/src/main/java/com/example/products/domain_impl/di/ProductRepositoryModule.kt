package com.example.products.domain_impl.di

import com.example.products.domain_impl.ProductRepositoryImpl
import com.example.products.repository.ProductRepository
import com.example.products.repository.remote.ProductRemoteApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ProductRepositoryModule {

    @Provides
    @Singleton
    fun provideGetInitialProductRepository(
        productRemote: ProductRemoteApi,
//        @IODispatcher dispatcher: CoroutineContext
    ): ProductRepository {
        return ProductRepositoryImpl(productRemote)//, dispatcher)
    }

}