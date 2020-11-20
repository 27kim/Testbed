package io.lab27.githubuser.data.datasource.remote

import io.lab27.githubuser.data.model.*
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response

interface AuthDataSource {
    suspend fun fetchMe(code : String) : FetchMeResponse
    suspend fun fetchToken(
        header: String,
        body: HashMap<String, String>
    ) : TokenResponse
//    suspend fun fetchEvent() : List<EventResponse>
}