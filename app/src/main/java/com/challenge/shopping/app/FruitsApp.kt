package com.challenge.shopping.app

import android.app.Application
import com.challenge.shopping.fruits.di.fruitsModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class FruitsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@FruitsApp)
            modules(fruitsModules)
        }
    }
}