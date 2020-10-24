package io.lab27.githubuser.data.datasource.local

import io.lab27.githubuser.data.dao.User
import io.lab27.githubuser.data.datasource.UserDataBase
import io.reactivex.Single

class LocalDataSourceImpl(private val db : UserDataBase) : LocalDataSource {
    override fun queryUsers(): Single<List<User>> {
        return db.userDao().select()
    }
}