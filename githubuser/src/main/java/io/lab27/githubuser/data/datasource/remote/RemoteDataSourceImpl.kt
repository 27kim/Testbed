package io.lab27.githubuser.data.datasource.remote

import io.lab27.githubuser.network.RetrofitManager
import io.lab27.githubuser.network.UserApi
import io.lab27.githubuser.network.UserResponse
import io.reactivex.Single

class RemoteDataSourceImpl : RemoteDataSource {
    override fun getUser(query: String): Single<UserResponse> {
        return RetrofitManager.build()
            .create(UserApi::class.java)
            .getUser(query)
    }
}