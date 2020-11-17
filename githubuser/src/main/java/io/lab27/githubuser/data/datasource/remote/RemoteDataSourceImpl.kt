package io.lab27.githubuser.data.datasource.remote

import io.lab27.githubuser.data.model.*
import io.lab27.githubuser.network.RetrofitManager
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response


class RemoteDataSourceImpl : RemoteDataSource {
    override fun getUser(query: String): Single<UserResponse> {
        return RetrofitManager
            .getInstance()
            .userApi
            .getUser(query)
    }

    override fun getUser_live(query: String): Call<UserResponse> {
        return RetrofitManager
            .getInstance()
            .userApi
            .getUser_live(query)
    }

    override suspend fun getUser_coroutines(query: String): UserResponse {
        return RetrofitManager
            .getInstance()
            .userApi
            .getUser_coroutines(query)
    }

    override suspend fun getUser_coroutines_p(query: String, page: Int): UserResponse {
        return RetrofitManager
            .getInstance()
            .userApi
            .getUser_coroutines_p(query, page)
    }

    override suspend fun getUser_coroutine_result(query: String): Response<UserResponse> {
        return RetrofitManager
            .getInstance()
            .userApi
            .getUser_coroutines_result(query)
    }

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