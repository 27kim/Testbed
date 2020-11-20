package io.lab27.githubuser.data

import io.lab27.githubuser.data.datasource.local.LocalDataSource
import io.lab27.githubuser.data.datasource.remote.AuthDataSource
import io.lab27.githubuser.data.datasource.remote.EventDataSource
import io.lab27.githubuser.data.model.EventResponse
import io.lab27.githubuser.data.model.FetchMeResponse
import io.lab27.githubuser.data.model.TokenResponse
import io.lab27.githubuser.util.L
import java.lang.Exception


class EventRepositoryImpl constructor(
    private val remote: EventDataSource,
    private val local: LocalDataSource
) :
    EventRepository {
    override suspend fun fetchEvent(): List<EventResponse> {
        return try {
            remote.fetchEvent()
        }catch (e : Exception){
            emptyList()
        }
    }
}

