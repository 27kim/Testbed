package io.lab27.githubuser.data.datasource.remote

import androidx.lifecycle.LiveData
import io.lab27.githubuser.data.dao.User
import io.lab27.githubuser.data.datasource.local.LocalDataSource
import io.lab27.githubuser.data.model.UserResponse
import io.lab27.githubuser.network.api.UserApi
import io.lab27.githubuser.util.Result
import io.reactivex.Single
import retrofit2.Call
import java.lang.Exception


interface UserRepository {
    fun fetchUserList(query: String): Single<UserResponse>
    fun fetchUserListLive(query: String): Call<UserResponse>

    fun queryAllUsers(): LiveData<List<User>>
    fun addFavorite(user: User)
    fun deleteFavorite(user: User)

    //coroutines test
    suspend fun fetchUserListCoroutines(query: String): UserResponse?
    suspend fun fetchUserListCoroutinesPaging(query: String, page: Int): UserResponse
    suspend fun queryUserListsCoroutines(): List<User>

    /**
     * testing
     * */
    suspend fun fetchUserListResult(query: String): Result<UserResponse>
}

class UserRepositoryImpl constructor(
    private val userApi: UserApi,
    private val local: LocalDataSource
) :
    UserRepository {
    override fun fetchUserList(query: String) = userApi.getUser(query)
    override fun queryAllUsers() = local.queryAllUsers()
    override fun addFavorite(user: User) = local.addFavorite(user)
    override fun deleteFavorite(user: User) = local.deleteFavorite(user)

    override fun fetchUserListLive(query: String): Call<UserResponse> {
        return userApi.getUser_live(query)
    }

    override suspend fun fetchUserListCoroutines(query: String): UserResponse? {
        return try {
            userApi.getUser_coroutines(query)
        } catch (t: Throwable) {
            null
        }
    }

    override suspend fun fetchUserListCoroutinesPaging(query: String, page: Int): UserResponse {
        return try {
            userApi.getUser_coroutines_p(query, page)
        } catch (e: Exception) {
            UserResponse()
        }
    }

    override suspend fun queryUserListsCoroutines() = local.queryAllUsers_c()

    override suspend fun fetchUserListResult(query: String): Result<UserResponse> {
        val response = userApi.getUser_coroutines_result(query)
        if (response.isSuccessful) {
            return Result.Success(response.body()!!)
        }
        return Result.Error(Exception("Error occured"))
    }
}
