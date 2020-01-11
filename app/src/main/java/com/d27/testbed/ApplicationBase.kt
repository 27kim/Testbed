package com.d27.testbed

import android.app.Application
import com.facebook.stetho.Stetho

class ApplicationBase : Application() {
    override fun onCreate() {
        super.onCreate()
        TestRepository.initialize(this)
        Stetho.initializeWithDefaults(this)
    }

}