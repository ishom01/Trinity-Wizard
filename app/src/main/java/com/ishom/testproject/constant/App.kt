package com.ishom.testproject.constant

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.Forest.plant

@HiltAndroidApp
class App: Application() {

    override fun onCreate() {
        super.onCreate()
    }
}