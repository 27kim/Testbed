package io.lab27.githubuser.network

import io.lab27.githubuser.data.model.UserResponse
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
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
        @Query("q") q: String?,
        @Query("per_page") page : Int = 3

    ): UserResponse

    @GET("search/users")
    suspend fun getUser_coroutines_p(
        @Query("q") q: String?,
        @Query("page") p : Int,
        @Query("per_page") page : Int = 3
    ): UserResponse

    @GET("search/users")
    suspend fun getUser_coroutines_result(
        @Query("q") q: String?
    ): Response<UserResponse>

}