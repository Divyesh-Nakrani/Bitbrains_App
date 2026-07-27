package com.bitcoding.bitbrains

import android.app.Application
import com.bitcoding.bitbrains.di.ServiceLocator
import com.bitcoding.bitbrains.push.AlertNotifier

/**
 * Application entry point: build the DI graph and create the notification
 * channels once, at process start (§4.3). Channel importance can never be
 * raised after creation, so they are defined correctly the first time.
 */
class BitBrainsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        ServiceLocator.init(this)
        AlertNotifier.createChannels(this)
    }
}
