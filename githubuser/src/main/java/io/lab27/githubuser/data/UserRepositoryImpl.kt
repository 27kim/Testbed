package io.lab27.githubuser.data

import io.lab27.githubuser.data.dao.User
import io.lab27.githubuser.data.datasource.local.LocalDataSource
import io.lab27.githubuser.data.datasource.remote.RemoteDataSource

class UserRepositoryImpl constructor(
    private val remote: RemoteDataSource,
    private val local: LocalDataSource
) :
    UserRepository {
    override fun fetchUserList(query: String) = remote.getUser(query)
    override fun queryUserLists() = local.queryUsers()
    override fun insertUser(user: User) = local.insertUser(user)
}