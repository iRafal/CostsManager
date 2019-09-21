package com.andrii.costsmanager

import android.app.Application
import com.andrii.costsmanager.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

/**
 * Created by Andrii Medvid on 8/4/2019.
 */
class CostsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        initModules()
    }

    private fun initModules() {
        startKoin {
            androidContext(this@CostsApplication)
            androidLogger()
            modules(appModule)
        }
    }
}