package com.example.products.domain_impl

import com.example.domain.util.RepositoryError
import com.example.domain.util.RequestResult
import com.example.products.mapper.mapToProduct
import com.example.products.model.Product
import com.example.products.repository.ProductRepository
import com.example.products.repository.remote.ProductRemoteApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productRemote: ProductRemoteApi
) : ProductRepository {
    override fun getProducts(): Flow<RequestResult<List<Product>, RepositoryError>> = flow {
        try {
            val initialData = productRemote.getProducts().map {
                it.mapToProduct()
            }
            emit(RequestResult.Success(initialData))
        } catch (e: HttpException) {
            when (e.code()) {
                404 -> emit(RequestResult.Error(RepositoryError.NetworkError.NO_INTERNET))
                else -> emit(RequestResult.Error(RepositoryError.NetworkError.SERVER_ERROR))
            }
        } catch (err: IOException) {
            emit(RequestResult.Error(RepositoryError.NetworkError.SERVER_ERROR))
        }
    }


    override fun getProductToById(id: Int): Flow<RequestResult<Product, RepositoryError>> =
        flow {
            try {
                val product = productRemote.getProductToById(id).mapToProduct()
                emit(RequestResult.Success(product))
            } catch (e: HttpException) {
                when (e.code()) {
                    404 -> emit(RequestResult.Error(RepositoryError.NetworkError.NO_INTERNET))
                    else -> emit(RequestResult.Error(RepositoryError.NetworkError.SERVER_ERROR))
                }
            } catch (err: IOException) {
                emit(RequestResult.Error(RepositoryError.NetworkError.SERVER_ERROR))
            }
        }
}