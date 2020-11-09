package io.lab27.githubuser.data

import androidx.lifecycle.LiveData
import io.lab27.githubuser.data.dao.User
import io.lab27.githubuser.network.UserResponse
import io.reactivex.Single
import retrofit2.Call

interface UserRepository {
    fun fetchUserList(query: String) : Single<UserResponse>
    fun fetchUserList_live(query: String) : Call<UserResponse>
    fun fetchUserList_result(query: String) : Call<Result<UserResponse>>

    fun queryAllUsers(): LiveData<List<User>>
    fun addFavorite(user : User)
    fun deleteFavorite(user : User)

    //coroutines test
    suspend fun fetchUserList_coroutines(query: String) : UserResponse
    suspend fun fetchUserList_coroutines_p(query: String, page : Int) : UserResponse
    suspend fun queryUserLists_coroutines(): List<User>


}