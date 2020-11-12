package io.lab27.githubuser.network

import io.lab27.githubuser.data.model.NewsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/top-headlines")
    suspend fun getHeadLines(
    ): NewsResponse
}