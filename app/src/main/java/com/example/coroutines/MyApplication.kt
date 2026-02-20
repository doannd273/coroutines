package com.example.coroutines

import android.app.Application
import com.example.coroutines.data.local.datastore.TokenManager
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application() {

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreate() {
        super.onCreate()

        initLogging()
        initTokenManager()
        initCrashlytics()
    }

    private fun initLogging() {
        if (BuildConfig.DEBUG) {
            Timber.Forest.plant(Timber.DebugTree())
        }
    }

    private fun initTokenManager() {
        CoroutineScope(Dispatchers.IO).launch {
            tokenManager.init()
        }
    }

    private fun initCrashlytics() {
        FirebaseCrashlytics.getInstance().isCrashlyticsCollectionEnabled = !BuildConfig.DEBUG
    }
}