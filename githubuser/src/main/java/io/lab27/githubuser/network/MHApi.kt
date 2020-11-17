package io.lab27.githubuser.network

import io.lab27.githubuser.data.model.EventResponse
import io.lab27.githubuser.data.model.NewsResponse
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface MHApi {
    @POST("api/menu/event/0")
    suspend fun fetchEvent(
    ): List<EventResponse>
}