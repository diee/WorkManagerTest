package com.example.navdrawer

import android.app.Application
import com.example.navdrawer.di.dataModule
import com.example.navdrawer.di.domainModule
import com.example.navdrawer.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NavApplication: Application() {

    companion object {
        lateinit var instance: NavApplication

        fun get(): NavApplication = instance
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        startKoin {
            androidLogger()
            androidContext(this@NavApplication)
            modules(listOf(dataModule, uiModule, domainModule))
        }
    }
}