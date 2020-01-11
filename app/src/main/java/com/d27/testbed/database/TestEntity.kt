package com.d27.testbed.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class TestEntity (
    @PrimaryKey
    val id : UUID = UUID.randomUUID(),
    val date : Date = Date(),
    val title : String = "",
    val desc : String = ""
)