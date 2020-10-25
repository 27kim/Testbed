package io.lab27.githubuser.data

import androidx.lifecycle.LiveData
import io.lab27.githubuser.data.dao.User
import io.lab27.githubuser.network.UserResponse
import io.reactivex.Single

interface UserRepository {
    fun fetchUserList(query: String) : Single<UserResponse>
    fun queryUserLists(): LiveData<List<User>>
    fun insertUser(user : User)
}