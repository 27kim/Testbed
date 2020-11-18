package io.lab27.githubuser.base

import android.app.Application
import io.lab27.githubuser.di.dataSourceModule
import io.lab27.githubuser.di.networkModule
import io.lab27.githubuser.di.repositoryModule
import io.lab27.githubuser.di.viewModelModule
import io.lab27.githubuser.util.AndroidCipherHelper
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidCipherHelper.init(this)
        startKoin {
            // use AndroidLogger as Koin Logger - default Level.INFO
            androidLogger(Level.DEBUG)
            // Android context
            androidContext(this@BaseApplication)
            // modules
            modules(listOf(viewModelModule, repositoryModule, dataSourceModule, networkModule))
        }
    }
}