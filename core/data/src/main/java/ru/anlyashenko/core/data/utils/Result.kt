package ru.anlyashenko.core.data.utils

// todo: возможно перенести
sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Error(val exception: Throwable, val message: String? = null) : Result<Nothing>
}