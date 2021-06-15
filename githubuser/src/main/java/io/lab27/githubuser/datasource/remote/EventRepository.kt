package io.lab27.githubuser.datasource.remote

import io.lab27.githubuser.datasource.local.LocalDataSource
import io.lab27.githubuser.data.model.EventResponse
import io.lab27.githubuser.network.api.MHApi
import java.lang.Exception


interface EventRepository {
    suspend fun fetchEvent(): List<EventResponse>
}

class EventRepositoryImpl constructor(
    private val mhApi: MHApi,
    private val local: LocalDataSource
) : EventRepository {
    override suspend fun fetchEvent(): List<EventResponse> {
        return try {
            mhApi.fetchEvent()
        } catch (e: Exception) {
            emptyList()
        }
    }
}

