package io.lab27.githubuser.data

import io.lab27.githubuser.data.dao.User
import io.lab27.githubuser.data.datasource.local.LocalDataSource
import io.lab27.githubuser.data.datasource.remote.UserDataSource
import io.lab27.githubuser.data.model.UserResponse
import io.lab27.githubuser.util.Result
import retrofit2.Call
import java.lang.Exception

class UserRepositoryImpl constructor(
    private val remote: UserDataSource,
    private val local: LocalDataSource
) :
    UserRepository {
    override fun fetchUserList(query: String) = remote.getUser(query)
    override fun queryAllUsers() = local.queryAllUsers()
    override fun addFavorite(user: User) = local.addFavorite(user)
    override fun deleteFavorite(user: User) = local.deleteFavorite(user)

    override fun fetchUserList_live(query: String): Call<UserResponse> {
        return remote.getUserLive(query)
    }

    override suspend fun fetchUserList_coroutines(query: String): UserResponse? {
        return try {
            remote.getUserCoroutines(query)
        } catch (t: Throwable) {
            null
        }
    }

    override suspend fun fetchUserList_coroutines_p(query: String, page: Int): UserResponse {
        return try {
            remote.getUserCoroutinesPaging(query, page)
        }catch (e : Exception){
            UserResponse()
        }
    }

    override suspend fun queryUserLists_coroutines() = local.queryAllUsers_c()

    override suspend fun fetchUserList_Result(query: String): Result<UserResponse> {
        val response = remote.getUserCoroutineResult(query)
        if(response.isSuccessful){
            return Result.Success(response.body()!!)
        }
        return Result.Error(Exception("Error occured"))
    }
}
