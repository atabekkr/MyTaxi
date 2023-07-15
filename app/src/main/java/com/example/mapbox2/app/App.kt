package com.example.mapbox2.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.mapbox2.di.appModule
import com.example.mapbox2.di.dataModule
import com.example.mapbox2.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    companion object {
        lateinit var instance : Application
    }

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(listOf(appModule, dataModule, domainModule))
        }
        instance = this
    }
}