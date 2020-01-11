package com.d27.testbed

import android.view.View
import androidx.lifecycle.ViewModel
import com.d27.testbed.database.TestEntity
import java.util.*

class TestViewModel : ViewModel(){
    private val repository = TestRepository.get()

    val getAllData = repository?.getAllData()

    fun addData(testEntity: TestEntity){
        repository?.addData(testEntity)
    }

    fun getSingleData(uuid: UUID){
        repository?.getSingleData(uuid)
    }

//    val singleData = repository?.getSingleData()
}