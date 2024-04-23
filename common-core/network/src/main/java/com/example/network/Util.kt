package com.example.network

import com.example.domain.util.RepositoryError
import retrofit2.HttpException
import java.io.IOException


fun Throwable.onRepositoryError(): RepositoryError {
    return when(this) {
        is HttpException -> {
            when(this.code()) {
                404 -> RepositoryError.NetworkError.NO_INTERNET
                else -> RepositoryError.NetworkError.SERVER_ERROR
            }
        }
        is IOException -> {
            RepositoryError.NetworkError.SERVER_ERROR
        }

        else -> {
            RepositoryError.NetworkError.SERVER_ERROR
        }
    }
}