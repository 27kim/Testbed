package io.lab27.githubuser.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import io.lab27.githubuser.data.dao.User
import io.lab27.githubuser.data.datasource.local.LocalDataSource
import io.lab27.githubuser.data.datasource.remote.RemoteDataSource
import io.lab27.githubuser.network.UserResponse
import io.lab27.githubuser.util.L
import io.lab27.githubuser.util.Result
import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import java.lang.Exception

class UserRepositoryImpl constructor(
    private val remote: RemoteDataSource,
    private val local: LocalDataSource
) :
    UserRepository {
    override fun fetchUserList(query: String) = remote.getUser(query)
    override fun queryAllUsers() = local.queryAllUsers()
    override fun addFavorite(user: User) = local.addFavorite(user)
    override fun deleteFavorite(user: User) = local.deleteFavorite(user)

    override fun fetchUserList_live(query: String): Call<UserResponse> {
        return remote.getUser_live(query)
//        LiveDataReactiveStreams.fromPublisher(
//            remote.getUser_live(query)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread()))
    }

    override suspend fun fetchUserList_coroutines(query: String): UserResponse? {
        return try {
            remote.getUser_coroutines(query)
        } catch (t: Throwable) {
            null
        }
    }

    override suspend fun fetchUserList_coroutines_p(query: String, page: Int): UserResponse {
        return remote.getUser_coroutines_p(query, page)
    }

    override suspend fun queryUserLists_coroutines() = local.queryAllUsers_c()

    override suspend fun fetchUserList_Result(query: String): Result<UserResponse> {
        val response = remote.getUser_coroutine_result(query)
        if(response.isSuccessful){
            return Result.Success(response.body()!!)
        }
        return Result.Error(Exception("Error occured"))
    }

    /**
     * temp
     * */

}

fun <T, K, R> LiveData<T>.combineWith(
    liveData: LiveData<K>,
    block: (T?, K?) -> R
): LiveData<R> {
    val result = MediatorLiveData<R>()
    result.addSource(this) {
        result.value = block(this.value, liveData.value)
    }
    result.addSource(liveData) {
        result.value = block(this.value, liveData.value)
    }
    return result
}