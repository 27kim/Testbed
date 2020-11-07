package com.deliveryhero.githubuser

import android.app.Application
import com.facebook.stetho.Stetho

class UserApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this);
        UserRepository.initialize(applicationContext)
    }
}