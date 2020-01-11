package com.d27.testbed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import com.d27.testbed.database.TestEntity
import java.util.*

class MainActivity : AppCompatActivity() {

    private val testViewModelProvider: TestViewModel by lazy {
        ViewModelProviders.of(this).get(TestViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val item = TestEntity(UUID.randomUUID(), Date(), "title1", "description")

        testViewModelProvider.addData(item)

        val result = testViewModelProvider.getAllData

        result?.observe(this, androidx.lifecycle.Observer {
            Log.i("aassddff", "시작==================================")
            it.forEach { testEntity ->
                Log.i("aassddff", "id : ${testEntity.id} / ${testEntity.date}")
            }
            Log.i("aassddff", "끄읏==================================")
        })
    }
}
