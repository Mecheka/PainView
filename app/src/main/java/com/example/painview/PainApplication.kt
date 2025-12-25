package com.example.painview

import android.app.Application
import com.example.painview.di.dataModule
import com.example.painview.di.networkModule
import com.example.painview.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@PainApplication)
            modules(listOf(networkModule, dataModule, uiModule))
        }
    }
}