package com.d27.testbed

import android.view.View
import androidx.lifecycle.ViewModel
import com.d27.testbed.database.TestEntity

class TestViewModel : ViewModel(){
    private val repository = TestRepository.get()

    val getAllData = repository?.getAllData()

    fun addData(testEntity: TestEntity){
        repository?.addData(testEntity)
    }

//    val singleData = repository?.getSingleData()
}