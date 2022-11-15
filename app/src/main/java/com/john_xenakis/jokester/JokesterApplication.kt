package com.john_xenakis.jokester

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import john_xenakis.jokester.BuildConfig
import timber.log.Timber

/**
 * The class that is compiled when Jokester app is created/launched.
 *
 * @since 10/4(Apr)/2022
 * @author Ioannis Xenakis
 * @version 1.0.0-alpha
 */
@HiltAndroidApp
class JokesterApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}