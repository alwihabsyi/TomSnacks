package com.nesha.tomsnacks.utils

sealed class UiState<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T): UiState<T>(data)
    class Error<T>(message: String): UiState<T>(message = message)
    class Loading<T>: UiState<T>()
}