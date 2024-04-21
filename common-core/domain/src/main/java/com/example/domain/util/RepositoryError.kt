package com.example.domain.util

sealed interface RepositoryError : Error{

    enum class NetworkError: RepositoryError {
        SERVER_ERROR,
        NO_INTERNET
    }

}