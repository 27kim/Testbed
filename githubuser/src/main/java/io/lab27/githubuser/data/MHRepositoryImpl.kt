package io.lab27.githubuser.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import io.lab27.githubuser.data.dao.User
import io.lab27.githubuser.data.datasource.local.LocalDataSource
import io.lab27.githubuser.data.datasource.remote.RemoteDataSource
import io.lab27.githubuser.data.model.EventResponse
import io.lab27.githubuser.data.model.NewsResponse
import io.lab27.githubuser.data.model.UserResponse
import io.lab27.githubuser.util.L
import io.lab27.githubuser.util.Result
import retrofit2.Call
import java.lang.Exception

class MHRepositoryImpl constructor(
    private val remote: RemoteDataSource,
    private val local: LocalDataSource
) :
    MHRepository {
    override suspend fun fetchEvent(): List<EventResponse> {
        return try {
            remote.fetchEvent()
        } catch (e: Exception) {
            L.e("MHRepositoryImpl e : ${e.message}")
            emptyList<EventResponse>()
        }

    }


}
