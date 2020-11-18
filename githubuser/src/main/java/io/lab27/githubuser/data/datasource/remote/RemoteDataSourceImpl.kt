package io.lab27.githubuser.data.datasource.remote

import io.lab27.githubuser.data.model.*
import io.lab27.githubuser.network.RetrofitManager
import io.lab27.githubuser.network.api.UserApi
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response


class RemoteDataSourceImpl(private val userApi: UserApi) : RemoteDataSource {
    override fun getUser(query: String): Single<UserResponse> = userApi.getUser(query)

    override fun getUser_live(query: String) = userApi.getUser_live(query)

    override suspend fun getUser_coroutines(query: String): UserResponse =
        userApi.getUser_coroutines(query)

    override suspend fun getUser_coroutines_p(query: String, page: Int) =
        userApi.getUser_coroutines_p(query, page)

    override suspend fun getUser_coroutine_result(query: String) =
        userApi.getUser_coroutines_result(query)

    override suspend fun fetchNewsHeadLines(): NewsResponse {
        return RetrofitManager
            .getInstance()
            .newsApi
            .getHeadLines()
    }

    override suspend fun fetchMe(token: String): FetchMeResponse {
        return RetrofitManager
            .getInstance()
            .authApi
            .fetchMe(token)
    }

    override suspend fun fetchToken(
        header: String,
        body: HashMap<String, String>
    ): TokenResponse {
        return RetrofitManager
            .getInstance()
            .authApi
            .fetchToken(header, body)
    }

    override suspend fun fetchEvent(): List<EventResponse> {
        return RetrofitManager
            .getInstance()
            .mhApi
            .fetchEvent()
    }

}