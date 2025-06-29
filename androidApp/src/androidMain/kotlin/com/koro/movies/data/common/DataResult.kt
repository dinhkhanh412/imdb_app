package com.koro.movies.data.common


sealed class DataResult<out T> {
    data class FromLocal<out T>(val data: T) : DataResult<T>()
    data class FromRemote<out T>(val data: T) : DataResult<T>()
    data class Error(val exception: Throwable) : DataResult<Nothing>()
}
