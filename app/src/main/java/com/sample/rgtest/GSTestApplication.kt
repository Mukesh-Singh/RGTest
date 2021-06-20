package com.sample.rgtest

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GSTestApplication:Application() {
    override fun onCreate() {
        super.onCreate()
    }
}