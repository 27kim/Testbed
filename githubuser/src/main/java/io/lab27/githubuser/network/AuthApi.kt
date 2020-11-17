package io.lab27.githubuser.network

import io.lab27.githubuser.data.model.FetchMeResponse
import io.lab27.githubuser.data.model.NewsResponse
import io.lab27.githubuser.data.model.TokenResponse
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface AuthApi {
    @GET("api/me")
    suspend fun fetchMe(
        @Header("Authorization") authorization: String
    ): FetchMeResponse

    @POST("oauth/token")
    @FormUrlEncoded
    suspend fun fetchToken(
        @Header("Authorization") header : String,
        @FieldMap data : HashMap<String, String>
    ): TokenResponse
}
