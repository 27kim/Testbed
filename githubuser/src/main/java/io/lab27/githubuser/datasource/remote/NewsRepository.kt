package io.lab27.githubuser.datasource.remote

import io.lab27.githubuser.datasource.local.LocalDataSource
import io.lab27.githubuser.data.model.NewsResponse
import io.lab27.githubuser.network.api.NewsApi
import io.lab27.githubuser.util.L
import java.lang.Exception

interface NewsRepository {
    suspend fun fetchNewsHeadLines() : NewsResponse?
}

class NewsRepositoryImpl constructor(
    private val newsApi: NewsApi,
    private val local: LocalDataSource
) :
    NewsRepository {
    override suspend fun fetchNewsHeadLines(): NewsResponse? {
        return try{
            newsApi.getHeadLines()
        }catch (e : Exception){
            L.e("fetchNewsHeadLines ${e.message}")
            null
        }
    }
}
