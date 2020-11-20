package io.lab27.githubuser.data

import io.lab27.githubuser.data.datasource.local.LocalDataSource
import io.lab27.githubuser.data.datasource.remote.NewsDataSource
import io.lab27.githubuser.data.model.NewsResponse
import io.lab27.githubuser.util.L
import java.lang.Exception

class NewsRepositoryImpl constructor(
    private val remote: NewsDataSource,
    private val local: LocalDataSource
) :
    NewsRepository {
    override suspend fun fetchNewsHeadLines(): NewsResponse? {
        return try{
            remote.fetchNewsHeadLines()
        }catch (e : Exception){
            L.e("fetchNewsHeadLines ${e.message}")
            null
        }
    }
}
