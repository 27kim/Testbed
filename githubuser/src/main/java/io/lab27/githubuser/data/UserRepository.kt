package io.lab27.githubuser.data

import androidx.lifecycle.LiveData
import io.lab27.githubuser.data.dao.User
import io.lab27.githubuser.network.UserResponse
import io.reactivex.Single
import retrofit2.Call

interface UserRepository {
    fun fetchUserList(query: String) : Single<UserResponse>
    fun fetchUserList_live(query: String) : Call<UserResponse>
//    fun fetchUserList_live(query: String) : LiveData<List<User>>

    fun queryUserLists(): LiveData<List<User>>
    fun addFavorite(user : User)
    fun deleteFavorite(user : User)
}