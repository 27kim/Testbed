package io.lab27.githubuser.data.datasource.local

import androidx.lifecycle.LiveData
import io.lab27.githubuser.data.dao.User
import io.reactivex.Single

interface LocalDataSource {
    fun queryAllUsers() : LiveData<List<User>>
    fun addFavorite(user : User)
    fun deleteFavorite(user : User)
}
