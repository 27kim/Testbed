package io.lab27.githubuser.data.datasource.remote

import io.lab27.githubuser.network.RetrofitManager
import io.lab27.githubuser.network.UserApi
import io.lab27.githubuser.network.UserResponse
import io.reactivex.Single
import retrofit2.Call
import io.lab27.githubuser.util.Result
import retrofit2.Response

class RemoteDataSourceImpl : RemoteDataSource {
    override fun getUser(query: String): Single<UserResponse> {
        return RetrofitManager.build()
            .create(UserApi::class.java)
            .getUser(query)
    }

    override fun getUser_live(query: String): Call<UserResponse> {
        return RetrofitManager.build()
            .create(UserApi::class.java)
            .getUser_live(query)
    }

    override suspend fun getUser_coroutines(query: String): UserResponse {
        return RetrofitManager.build()
            .create(UserApi::class.java)
            .getUser_coroutines(query)
    }

    override suspend fun getUser_coroutines_p(query: String, page : Int): UserResponse {
        return RetrofitManager.build()
            .create(UserApi::class.java)
            .getUser_coroutines_p(query, page)
    }

    override suspend fun getUser_coroutine_result(query: String): Response<UserResponse> {
        return RetrofitManager.build()
            .create(UserApi::class.java)
            .getUser_coroutines_result(query)
    }

}