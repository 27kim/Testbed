package io.lab27.githubuser.data.datasource.local

import androidx.lifecycle.LiveData
import io.lab27.githubuser.data.dao.User
import io.reactivex.Single

interface LocalDataSource {
    fun queryUsers() : LiveData<List<User>>
    fun insertUser(user : User)
}
