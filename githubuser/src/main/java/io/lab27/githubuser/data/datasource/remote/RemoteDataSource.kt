package io.lab27.githubuser.data.datasource.remote

import io.lab27.githubuser.data.model.*
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response

interface RemoteDataSource {
    fun getUser(query: String): Single<UserResponse>
    fun getUser_live(query: String): Call<UserResponse>

    suspend fun getUser_coroutines(query: String): UserResponse
    suspend fun getUser_coroutines_p(query: String, page: Int): UserResponse
    suspend fun getUser_coroutine_result(query: String): Response<UserResponse>

    suspend fun fetchNewsHeadLines() : NewsResponse

    suspend fun fetchMe(code : String) : FetchMeResponse
    suspend fun fetchToken(
        header: String,
        body: HashMap<String, String>
    ) : TokenResponse
    suspend fun fetchEvent() : List<EventResponse>
}