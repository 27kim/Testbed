package io.lab27.githubuser.data.datasource.local

import io.lab27.githubuser.data.dao.User
import io.reactivex.Single

interface LocalDataSource {
    fun queryUsers() : Single<List<User>>
}
