package io.lab27.githubuser.util

sealed class Result<out T : Any> {
    data class Success<out T :Any>(val data : T) : Result<T>()
    data class Failure(val exception : String) : Result<Nothing>()
}