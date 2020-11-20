package io.lab27.githubuser.data

import io.lab27.githubuser.data.model.EventResponse
import io.lab27.githubuser.data.model.FetchMeResponse
import io.lab27.githubuser.data.model.TokenResponse

interface EventRepository {
    suspend fun fetchEvent(): List<EventResponse>
}