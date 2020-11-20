package io.lab27.githubuser.data.datasource.remote

import io.lab27.githubuser.data.model.*
import io.lab27.githubuser.network.RetrofitManager
import io.lab27.githubuser.network.api.NewsApi
import io.lab27.githubuser.network.api.UserApi
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response


class NewsDataSourceImpl(private val newsApi: NewsApi) : NewsDataSource {
    override suspend fun fetchNewsHeadLines() = newsApi.getHeadLines()
}