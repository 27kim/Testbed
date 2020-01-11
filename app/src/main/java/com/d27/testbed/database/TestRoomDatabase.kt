package com.d27.testbed.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.d27.TestDao

@Database(entities = [TestEntity::class],version = 1)
@TypeConverters(TestTypeConverters::class)
abstract class TestRoomDatabase : RoomDatabase(){
    abstract fun testDao() : TestDao
}