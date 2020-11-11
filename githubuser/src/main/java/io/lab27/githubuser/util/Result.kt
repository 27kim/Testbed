package io.lab27.githubuser.util

//sealed class Result<out R> {
//    data class Success<out T>(val data: T) : Result<T>()
//    data class Error(val exception: Exception) : Result<Nothing>()
//}

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}