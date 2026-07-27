package com.bitcoding.bitbrains.di

import android.content.Context
import com.bitcoding.bitbrains.data.local.AppDatabase
import com.bitcoding.bitbrains.data.local.SecureStore
import com.bitcoding.bitbrains.data.remote.ApiClient
import com.bitcoding.bitbrains.data.repository.AlertRepository
import com.bitcoding.bitbrains.data.repository.AuthRepository
import com.bitcoding.bitbrains.data.repository.DeviceRepository

/**
 * Tiny manual DI. Everything is lazily built off the application context and
 * lives for the process lifetime, so Workers, the FCM service, and Activities
 * all share one SecureStore / ApiClient / Room instance.
 */
object ServiceLocator {

    @Volatile private var initialized = false

    lateinit var secureStore: SecureStore private set
    lateinit var apiClient: ApiClient private set
    lateinit var authRepository: AuthRepository private set
    lateinit var deviceRepository: DeviceRepository private set
    lateinit var alertRepository: AlertRepository private set

    fun init(context: Context) {
        if (initialized) return
        synchronized(this) {
            if (initialized) return
            val app = context.applicationContext
            secureStore = SecureStore(app)
            apiClient = ApiClient(secureStore)
            val db = AppDatabase.get(app)
            authRepository = AuthRepository(apiClient, secureStore)
            deviceRepository = DeviceRepository(apiClient, secureStore)
            alertRepository = AlertRepository(apiClient, db.alertDao(), secureStore)
            initialized = true
        }
    }

    /** Safe accessor for entry points (Workers) that may run before Application.onCreate. */
    fun ensure(context: Context) = init(context)
}
