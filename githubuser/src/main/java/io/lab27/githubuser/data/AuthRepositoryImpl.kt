package io.lab27.githubuser.data

import io.lab27.githubuser.data.datasource.local.LocalDataSource
import io.lab27.githubuser.data.datasource.remote.RemoteDataSource
import io.lab27.githubuser.data.model.EventResponse
import io.lab27.githubuser.data.model.FetchMeResponse
import io.lab27.githubuser.data.model.TokenResponse
import io.lab27.githubuser.util.L
import java.lang.Exception

class AuthRepositoryImpl constructor(
    private val remote: RemoteDataSource,
    private val local: LocalDataSource
) :
    AuthRepository {
    override suspend fun fetchToken(
        header: String,
        body: HashMap<String, String>
    ): TokenResponse {
        return try{
            remote.fetchToken(header, body)
        }catch (e: Exception){
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

    override suspend fun fetchEvent(): List<EventResponse> {
        return try {
            remote.fetchEvent()
        } catch (e: Exception) {
            L.e("fetchEvent exception ${e.message}")
            emptyList<EventResponse>()
        }
    }

}
