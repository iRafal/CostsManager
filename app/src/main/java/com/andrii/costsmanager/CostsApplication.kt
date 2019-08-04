package com.andrii.costsmanager

import android.app.Application
import timber.log.Timber

/**
 * Created by Andrii Medvid on 8/4/2019.
 */
class CostsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}