package io.lab27.githubuser.data.datasource.remote

import io.lab27.githubuser.data.model.*
import io.lab27.githubuser.network.api.AuthApi
import io.lab27.githubuser.network.api.MHApi
import io.lab27.githubuser.network.api.UserApi
import io.reactivex.Single


class EventDataSourceImpl(private val mhApi: MHApi) : EventDataSource {
    override suspend fun fetchEvent() = mhApi.fetchEvent()
}