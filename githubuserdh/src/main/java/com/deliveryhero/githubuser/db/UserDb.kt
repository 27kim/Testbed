package com.deliveryhero.githubuser.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.deliveryhero.githubuser.network.User

@Database(entities = [User::class], version = 1)
abstract class UserDb() : RoomDatabase() {
    abstract fun userDao() : UserDao
}