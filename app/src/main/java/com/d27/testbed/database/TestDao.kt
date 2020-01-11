package com.d27

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.d27.testbed.database.TestEntity
import java.util.*

@Dao
interface TestDao {
    @Query("select * from testentity")
    fun getAllData(): LiveData<List<TestEntity>>

    @Query("select * from testentity where id = :id")
    fun getSingleData(id: UUID) : LiveData<TestEntity>

    @Insert
    fun addData(testEntity: TestEntity)

    @Update
    fun updateData(testEntity: TestEntity)
}