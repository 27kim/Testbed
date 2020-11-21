package io.lab27.githubuser.data.datasource.remote

import io.lab27.githubuser.data.datasource.local.LocalDataSource
import io.lab27.githubuser.data.model.FetchMeResponse
import io.lab27.githubuser.data.model.TokenResponse
import io.lab27.githubuser.network.api.AuthApi
import io.lab27.githubuser.util.L

interface AuthRepository {
    suspend fun fetchToken(
        header: String,
        body: HashMap<String, String>
    ): TokenResponse

    suspend fun fetchMe(token: String): FetchMeResponse
}

class AuthRepositoryImpl constructor(
    private val authApi: AuthApi,
    private val local: LocalDataSource
) :
    AuthRepository {
    override suspend fun fetchToken(
        header: String,
        body: HashMap<String, String>
    ): TokenResponse {
        return try {
            authApi.fetchToken(header, body)
        } catch (e: Exception) {
            L.e("fetchToken exception ${e.message}")
            TokenResponse()
        }
    }

    override suspend fun fetchMe(token: String): FetchMeResponse {
        return try {
            authApi.fetchMe(token)
        } catch (e: Exception) {
            L.e("fetchMe exception ${e.message}")
            FetchMeResponse()
        }

    }
}

