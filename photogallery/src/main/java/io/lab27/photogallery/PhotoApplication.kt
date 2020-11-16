package io.lab27.photogallery

import android.app.Application
import io.lab27.photogallery.module.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PhotoApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

    private fun startKoin() {
        startKoin {
            androidLogger()
            androidContext(this@PhotoApplication)
            modules(appModule)
        }
    }
}