package io.lab27.githubuser

import io.lab27.githubuser.network.UserApi
import io.lab27.githubuser.network.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(val remote : UserApi) {
    fun fetchUserList(query : String) {
        remote.getUser(query)?.enqueue(object : Callback<UserResponse?>{
            override fun onFailure(call: Call<UserResponse?>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call<UserResponse?>, response: Response<UserResponse?>) {
                TODO("Not yet implemented")
            }

        }

        )
    }
}