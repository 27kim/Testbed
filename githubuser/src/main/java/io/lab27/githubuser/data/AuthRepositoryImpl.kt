package io.lab27.githubuser.data

import io.lab27.githubuser.data.datasource.local.LocalDataSource
import io.lab27.githubuser.data.datasource.remote.AuthDataSource
import io.lab27.githubuser.data.model.FetchMeResponse
import io.lab27.githubuser.data.model.TokenResponse
import io.lab27.githubuser.util.L


class AuthRepositoryImpl constructor(
    private val remote: AuthDataSource,
    private val local: LocalDataSource
) :
    AuthRepository {
    override suspend fun fetchToken(
        header: String,
        body: HashMap<String, String>
    ): TokenResponse {
        return try {
            remote.fetchToken(header, body)
        } catch (e: Exception) {
            L.e("fetchToken exception ${e.message}")
            TokenResponse()
        }
    }

    override suspend fun fetchMe(token: String): FetchMeResponse {
        return try {
            remote.fetchMe(token)
        } catch (e: Exception) {
            L.e("fetchMe exception ${e.message}")
            FetchMeResponse()
        }

    }
}

