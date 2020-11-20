package io.lab27.githubuser.data.datasource.remote

import io.lab27.githubuser.data.model.*
import io.lab27.githubuser.network.api.UserApi
import io.reactivex.Single


class UserDataSourceImpl(private val userApi: UserApi) : UserDataSource {
    override fun getUser(query: String): Single<UserResponse> = userApi.getUser(query)

    override fun getUserLive(query: String) = userApi.getUser_live(query)

    override suspend fun getUserCoroutines(query: String): UserResponse =
        userApi.getUser_coroutines(query)

    override suspend fun getUserCoroutinesPaging(query: String, page: Int) =
        userApi.getUser_coroutines_p(query, page)

    override suspend fun getUserCoroutineResult(query: String) =
        userApi.getUser_coroutines_result(query)
}