package io.lab27.githubuser.data.datasource.remote

import androidx.lifecycle.LiveData
import io.lab27.githubuser.data.dao.User
import io.lab27.githubuser.network.UserResponse
import io.reactivex.Single
import retrofit2.Call

interface RemoteDataSource {
    fun getUser(query: String): Single<UserResponse>
    fun getUser_live(query: String): Call<UserResponse>
    fun getUser_result(query: String): Call<Result<UserResponse>>
    suspend fun getUser_coroutines(query: String): UserResponse

    //    fun getUser_live(query: String): LiveData<List<User>>
    suspend fun getUser_coroutines_p(query: String, page: Int): UserResponse
}