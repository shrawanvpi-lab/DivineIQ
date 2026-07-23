package com.divineiq.app

import android.app.Application
import com.divineiq.app.utils.NotificationHelper

/**
 * Application entry point for DivineIQ. Owns the single [AppContainer]
 * instance that every Fragment/ViewModel pulls its repositories from via
 * [com.divineiq.app.viewmodel.ViewModelFactory].
 */
class DivineIQApplication : Application() {

    lateinit var appContainer: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
        NotificationHelper.ensureChannel(this)
    }
}
