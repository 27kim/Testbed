package com.deliveryhero.githubuser.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.deliveryhero.githubuser.network.User

@Dao
interface UserDao{
    @Query("Select * from user")
    fun getAllUsers(): LiveData<List<User>>

    @Query("Select * from user where login = :userId")
    fun getSingleUser(userId : String): LiveData<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user : User)

    @Delete
    fun removeUser(user : User)

    @Update
    fun updateUser(user : User)
}