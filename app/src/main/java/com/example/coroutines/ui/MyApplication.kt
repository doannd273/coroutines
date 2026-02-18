package com.example.coroutines.ui

import android.app.Application
import com.example.coroutines.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * - Gọi api lấy danh sách user
 * - Hiện thị RecyclerView
 * - Có loading
 * - KHông block Main Thread
 * - Dùng MVVM + Coroutines
 */
@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Only log environment debug
        if (BuildConfig.DEBUG) {
            Timber.Forest.plant(Timber.DebugTree())
        }
    }
}