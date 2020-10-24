package io.lab27.githubuser.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.lab27.githubuser.base.BaseDao
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface UserDao : BaseDao<User>{
    @Query("Select * from User")
    fun select(): Single<List<User>>

    @Insert
    override fun insert(obj: User)
}