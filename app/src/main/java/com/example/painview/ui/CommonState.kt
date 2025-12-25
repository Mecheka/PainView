package com.example.painview.ui

sealed interface CommonState<out T> {
    data object Loading : CommonState<Nothing>
    data class Error(val message: String) : CommonState<Nothing>
    data class Success<T>(val data: T) : CommonState<T>
}

