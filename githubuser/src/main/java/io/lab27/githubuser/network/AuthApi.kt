package io.lab27.githubuser.network

import io.lab27.githubuser.data.model.NewsResponse
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface AuthApi {
    @Multipart
    @POST("oauth/token")
    suspend fun getToKen(
        @Header("Authorization") authorization: String = "Basic Bd96aMYBFL6k9Hm2mMSQrPui vSoE7Xmsl6oIp4yJwmJpE5Ec8pOk86dgkyWOwpUGVYiumYLp",
        @Part part: MultipartBody.Part
    ): String
}