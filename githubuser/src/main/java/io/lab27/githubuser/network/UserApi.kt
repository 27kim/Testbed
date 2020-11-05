package io.lab27.githubuser.network

import androidx.lifecycle.LiveData
import io.lab27.githubuser.data.dao.User
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {
    @GET("search/users")
    fun getUser(
        @Query("q") q: String?
    ): Single<UserResponse>

    @GET("search/users")
    fun getUser_live(
        @Query("q") q: String?
    ): Call<UserResponse>

    @GET("search/users")
    suspend fun getUser_coroutines(
        @Query("q") q: String?
    ): UserResponse
}