package io.lab27.githubuser.data.datasource.remote

import io.lab27.githubuser.data.model.*
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response

interface EventDataSource {
    suspend fun fetchEvent() : List<EventResponse>
}