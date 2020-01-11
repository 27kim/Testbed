package com.d27.testbed

import android.content.Context
import androidx.room.Room
import com.d27.testbed.database.TestEntity
import com.d27.testbed.database.TestRoomDatabase
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executors

private const val DB_NAME = "test_db"

class TestRepository private constructor(context: Context) {

    private val database = Room.databaseBuilder(
        context
        , TestRoomDatabase::class.java
        , DB_NAME
    ).build()

    private val testDao = database.testDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getAllData() = testDao.getAllData()
    fun getSingleData(uuid: UUID) = testDao.getSingleData(uuid)
    fun addData(testEntity: TestEntity) {
        executor.execute {
            testDao.addData(testEntity)
        }
    }

    fun updateData(testEntity: TestEntity) {
        executor.execute {
            testDao.updateData(testEntity)
        }
    }
    companion object {
        private var INSTANCE: TestRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = TestRepository(context)
            }
        }

        fun get(): TestRepository? {
            return INSTANCE ?: throw IllegalStateException("TestRepository must be initialized")
        }
    }
}