package com.pettcare.app

import android.app.Application
import com.pettcare.app.auth.login.data.di.authDataModule
import com.pettcare.app.auth.login.domain.di.authDomainModule
import com.pettcare.app.auth.login.presentation.di.loginPresentationModule
import com.pettcare.app.auth.signin.domain.di.signInDomainModule
import com.pettcare.app.auth.signin.network.di.firebaseModule
import com.pettcare.app.auth.signin.presentation.di.signInPresentationModule
import com.pettcare.app.navigation.di.navigationModule
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
                    navigationModule,
                    loginPresentationModule,
                    authDomainModule,
                    authDataModule,
                    signInPresentationModule,
                    signInDomainModule,
                    firebaseModule,
                ),
            )
        }
    }
}
