package io.lab27.githubuser.data.datasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.lab27.githubuser.data.dao.User
import io.lab27.githubuser.data.dao.UserDao

@Database(
    entities = [User::class], version = 1
)
abstract class UserDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {

        private var INSTANCE: UserDataBase? = null

        fun getInstance(context: Context): UserDataBase? {

            if (INSTANCE == null) {
                synchronized(UserDataBase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        UserDataBase::class.java,
                        "person.db"
                    )
                        .build()
                }
            }

            return INSTANCE
        }
    }
}