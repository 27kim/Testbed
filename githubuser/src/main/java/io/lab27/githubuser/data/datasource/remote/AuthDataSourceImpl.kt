package io.lab27.githubuser.data.datasource.remote

import io.lab27.githubuser.data.model.*
import io.lab27.githubuser.network.api.AuthApi
import io.lab27.githubuser.network.api.UserApi
import io.reactivex.Single


class AuthDataSourceImpl(private val authApi: AuthApi) : AuthDataSource {

    override suspend fun fetchMe(token: String): FetchMeResponse = authApi.fetchMe(token)

    override suspend fun fetchToken(
        header: String,
        body: HashMap<String, String>
    ): TokenResponse = authApi.fetchToken(header, body)

//    override suspend fun fetchEvent(): List<EventResponse> {
//        return RetrofitManager
//            .getInstance()
//            .mhApi
//            .fetchEvent()
//    }
}