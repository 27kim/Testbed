package com.deliveryhero.githubuser

import android.content.Context
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.deliveryhero.githubuser.db.UserDb
import com.deliveryhero.githubuser.network.RetrofitManager
import com.deliveryhero.githubuser.network.User
import com.deliveryhero.githubuser.network.UserResponse
import com.deliveryhero.githubuser.network.api.UserApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors

private const val DATABASE_NAME = "user-database"

class UserRepository(context: Context) {

    private val executor = Executors.newSingleThreadExecutor()
    private val userDb = Room
        .databaseBuilder(context, UserDb::class.java, DATABASE_NAME)
        .build()

    private val userApi: UserApi by lazy {
        RetrofitManager.build().create(UserApi::class.java)
    }

    fun getUserData(source: String): MutableLiveData<List<User>>? {
        val mediatorLiveData = MediatorLiveData<List<User>>()
        val apiUserData: MutableLiveData<List<User>> = MutableLiveData<List<User>>()

        userApi.getUser(source)?.enqueue(object : Callback<UserResponse?> {
            override fun onResponse(
                call: Call<UserResponse?>?,
                response: Response<UserResponse?>
            ) {
                if (response.isSuccessful) {
                    apiUserData.value = response.body()?.items
                } else {
                    apiUserData.value = null
                }
            }

            override fun onFailure(call: Call<UserResponse?>?, t: Throwable?) {
                Log.e("ERROR", t?.message)
                apiUserData.value = null
            }
        })

        val dbData = userDb.userDao().getAllUsers()

        mediatorLiveData.addSource(dbData) {
            mediatorLiveData.value = combineData(dbData, apiUserData)
        }
        mediatorLiveData.addSource(apiUserData) {
            mediatorLiveData.value = combineData(dbData, apiUserData)
        }

        return mediatorLiveData
    }

    private fun combineData(
        dbData: LiveData<List<User>>,
        apiData: MutableLiveData<List<User>>
    ): List<User>? {
        if (dbData.value == null || apiData.value == null) return null
        var apiResult = apiData.value!!
        val dbResult = dbData.value!!

        for ((index, apiItem) in apiResult.withIndex()) {
            if (dbResult.isNotEmpty()) {
                for (dbItem in dbData.value!!) {
                    if (apiItem.login == dbItem.login) {
                        apiResult[index].isFavorite = dbItem.isFavorite
                        break
                    } else {
                        apiResult[index].isFavorite = false
                    }
                }
            } else {
                apiResult[index].isFavorite = false
            }
        }
        return apiResult
    }

    fun getUserFromDb(userId: String): LiveData<User> {
        return userDb.userDao().getSingleUser(userId)
    }

    fun getAllUserFromDb(): LiveData<List<User>> {
        return userDb.userDao().getAllUsers()
    }

    fun addUser(user: User) {
        executor.execute {
            userDb.userDao().addUser(user)
        }
    }

    fun removeUser(user: User) {
        executor.execute {
            userDb.userDao().removeUser(user)
        }
    }

    companion object {
        private var INSTANCE: UserRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = UserRepository(context)
            }
        }

        fun getInstance(): UserRepository {
            return INSTANCE ?: throw IllegalArgumentException("UserRepository must be initialized")
        }
    }
}