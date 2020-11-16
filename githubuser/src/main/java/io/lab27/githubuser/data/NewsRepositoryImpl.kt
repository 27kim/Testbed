package io.lab27.githubuser.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import io.lab27.githubuser.data.dao.User
import io.lab27.githubuser.data.datasource.local.LocalDataSource
import io.lab27.githubuser.data.datasource.remote.RemoteDataSource
import io.lab27.githubuser.data.model.NewsResponse
import io.lab27.githubuser.data.model.UserResponse
import io.lab27.githubuser.util.L
import io.lab27.githubuser.util.Result
import retrofit2.Call
import java.lang.Exception

class NewsRepositoryImpl constructor(
    private val remote: RemoteDataSource,
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

    override suspend fun getAuth(): String {
        return try{
            remote.getAuth()
        }catch (e : Exception){
            L.e("TOKEN EXCEPTION ${e.message}")
            ""
        }
    }
}
