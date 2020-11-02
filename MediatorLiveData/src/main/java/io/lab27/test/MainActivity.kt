package io.lab27.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private val executor = Executors.newFixedThreadPool(10)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mediatorLiveData = MediatorLiveData<String>()

        val liveData1 = MutableLiveData<String>()
        val liveData2 = MutableLiveData<Int>()

        executor.execute {
            Thread.sleep(1000)
            liveData1.postValue("let's get it")
            Log.e("tag", "liveData1 submitted")
        }

        executor.execute {
            Thread.sleep(5000)
            liveData2.postValue(20200709)
            Log.e("tag", "liveData2 submitted")

        }

        mediatorLiveData.addSource(liveData1) {
            mediatorLiveData.value = combineData(liveData1, liveData2)
        }
        mediatorLiveData.addSource(liveData2) {
            mediatorLiveData.value = combineData(liveData1, liveData2)
        }

        /**
         * observe 해야 함!!!!!!!!!!!!!!!!!!!!!!!!!1
         * observe 해야 함!!!!!!!!!!!!!!!!!!!!!!!!!1
         * observe 해야 함!!!!!!!!!!!!!!!!!!!!!!!!!1
         * */

        mediatorLiveData.observe(this, Observer {
            Log.e("tag", "got data on mediatorLiveData")

            tv_text.text = it
        })
    }

    private fun combineData(liveData1: MutableLiveData<String>, liveData2: MutableLiveData<Int>): String? {
        if (liveData1.value == null) {
            val msg = "returned since liveData1 is ${liveData1.value}"
            Log.e("tag", msg)
            return msg
        }

        if (liveData2.value == null) {
            val msg = "returned since liveData2 is ${liveData2.value}"
            Log.e("tag", msg)
            return msg
        }

        return "${liveData1.value} / ${liveData2.value}"
    }
}