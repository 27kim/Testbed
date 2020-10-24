package io.lab27.githubuser.data

import io.lab27.githubuser.data.dao.User
import io.lab27.githubuser.network.UserResponse
import io.reactivex.Single

interface UserRepository {
    fun fetchUserList(query: String) : Single<UserResponse>
    fun queryUserLists(): Single<List<User>>
}