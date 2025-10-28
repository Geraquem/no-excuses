package com.mmfsin.noexcuses.base

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        disableNightMode()
    }

    private fun disableNightMode() = setDefaultNightMode(MODE_NIGHT_NO)
}