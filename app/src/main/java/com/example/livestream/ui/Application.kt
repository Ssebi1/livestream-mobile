package com.example.livestream.ui

import android.app.Application
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application : Application(), DefaultLifecycleObserver {

    companion object {
        var isAppOpen = false
    }

    override fun onCreate() {
        super<Application>.onCreate()
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        isAppOpen = false
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        isAppOpen = true
    }
}