package io.lab27.githubuser.datasource

import io.lab27.githubuser.network.UserResponse
import io.reactivex.Single

interface UserRepository {
    fun fetchUserList(query: String) : Single<UserResponse>
}