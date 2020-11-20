package io.lab27.githubuser.data

import io.lab27.githubuser.data.model.EventResponse
import io.lab27.githubuser.data.model.FetchMeResponse
import io.lab27.githubuser.data.model.TokenResponse

interface AuthRepository {
    suspend fun fetchToken(
        header: String,
        body: HashMap<String, String>
    ): TokenResponse

    suspend fun fetchMe(token: String): FetchMeResponse
//    suspend fun fetchEvent(): List<EventResponse>
}