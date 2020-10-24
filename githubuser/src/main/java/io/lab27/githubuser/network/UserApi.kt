package io.lab27.githubuser.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {
    @GET("search/users")
    fun getUser(
        @Query("q") q: String?
    ): Single<UserResponse>
}