package io.lab27.githubuser.datasource

import io.lab27.githubuser.datasource.remote.RemoteDataSource
import io.lab27.githubuser.network.UserApi

class UserRepositoryImpl constructor(private val remote : RemoteDataSource, private val local : String): UserRepository {
    override fun fetchUserList(query: String) = remote.getUser(query)
}