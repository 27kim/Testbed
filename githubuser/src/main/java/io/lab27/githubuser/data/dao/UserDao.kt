package io.lab27.githubuser.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import io.lab27.githubuser.base.BaseDao
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface UserDao : BaseDao<User> {
    @Query("Select * from User")
    fun queryAllUsers(): LiveData<List<User>>

    @Query("Select * from User")
    suspend fun queryAllUsers_c(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun insert(obj: User)
}