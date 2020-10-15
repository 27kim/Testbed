package io.lab27.githubuser.datasource.remote

import io.lab27.githubuser.network.UserResponse
import io.reactivex.Single

interface RemoteDataSource {
    fun getUser(query: String): Single<UserResponse>
}