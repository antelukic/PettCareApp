package com.pettcare.app

import android.app.Application
import com.pettcare.app.welcome.di.welcomeModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PettCareApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@PettCareApp)
            modules(
                listOf(
                    welcomeModule,
                ),
            )
        }
    }
}
