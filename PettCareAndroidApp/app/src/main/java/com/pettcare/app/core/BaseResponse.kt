package com.pettcare.app.core

sealed interface BaseResponse<out T> {

    data object Loading : BaseResponse<Nothing>

    sealed class Error : BaseResponse<Nothing> {
        data object Network : Error()
        data object Other : Error()
    }

    data class Success<out T>(val data: T) : BaseResponse<T>
}
