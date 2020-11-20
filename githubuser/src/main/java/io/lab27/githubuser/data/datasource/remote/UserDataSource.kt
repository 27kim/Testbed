package io.lab27.githubuser.data.datasource.remote

import io.lab27.githubuser.data.model.*
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response

interface UserDataSource {
    fun getUser(query: String): Single<UserResponse>
    fun getUserLive(query: String): Call<UserResponse>

    suspend fun getUserCoroutines(query: String): UserResponse
    suspend fun getUserCoroutinesPaging(query: String, page: Int): UserResponse
    suspend fun getUserCoroutineResult(query: String): Response<UserResponse>
}