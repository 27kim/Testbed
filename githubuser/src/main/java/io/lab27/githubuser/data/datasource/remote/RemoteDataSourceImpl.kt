package io.lab27.githubuser.data.datasource.remote

import androidx.lifecycle.LiveData
import io.lab27.githubuser.data.dao.User
import io.lab27.githubuser.network.RetrofitManager
import io.lab27.githubuser.network.UserApi
import io.lab27.githubuser.network.UserResponse
import io.reactivex.Single
import retrofit2.Call

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

    override fun getUser_result(query: String): Call<Result<UserResponse>>{
        TODO("Not yet implemented")
    }

    override suspend fun getUser_coroutines(query: String): UserResponse {
        return RetrofitManager.build()
            .create(UserApi::class.java)
            .getUser_coroutines(query)
    }
}