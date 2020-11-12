package io.lab27.githubuser.data

import androidx.lifecycle.LiveData
import io.lab27.githubuser.data.dao.User
import io.lab27.githubuser.data.model.NewsResponse
import io.lab27.githubuser.data.model.UserResponse
import io.lab27.githubuser.util.Result
import io.reactivex.Single
import retrofit2.Call

interface NewsRepository {
    suspend fun fetchNewsHeadLines() : NewsResponse?
}