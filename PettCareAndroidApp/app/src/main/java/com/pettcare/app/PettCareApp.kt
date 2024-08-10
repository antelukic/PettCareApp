package com.pettcare.app

import android.app.Application
import com.pettcare.app.auth.login.data.di.authDataModule
import com.pettcare.app.auth.login.domain.di.authDomainModule
import com.pettcare.app.auth.login.presentation.di.loginPresentationModule
import com.pettcare.app.auth.network.di.authNetworkModule
import com.pettcare.app.auth.signin.domain.di.signInDomainModule
import com.pettcare.app.auth.signin.network.di.firebaseModule
import com.pettcare.app.auth.signin.presentation.di.signInPresentationModule
import com.pettcare.app.bottomnav.di.bottomNavigationModule
import com.pettcare.app.chat.data.di.chatDataModule
import com.pettcare.app.chat.domain.di.chatDomainModule
import com.pettcare.app.chat.network.di.chatNetworkModule
import com.pettcare.app.chat.presentation.di.chatPresentationModule
import com.pettcare.app.core.network.di.networkModule
import com.pettcare.app.create.data.di.createFeatureDataModule
import com.pettcare.app.create.domain.di.createFeatureDomainModule
import com.pettcare.app.create.network.di.createFeatureNetworkModule
import com.pettcare.app.create.presentation.di.createFeaturePresentationModule
import com.pettcare.app.home.domain.di.homeDomainModule
import com.pettcare.app.home.model.di.homeModelModule
import com.pettcare.app.home.network.di.homeNetworkModule
import com.pettcare.app.home.presentation.di.homeUiModule
import com.pettcare.app.navigation.di.navigationModule
import com.pettcare.app.profile.data.di.profileDataModule
import com.pettcare.app.profile.domain.di.profileDomainModule
import com.pettcare.app.profile.network.di.profileNetworkModule
import com.pettcare.app.profile.presentation.di.profilePresentationModule
import com.pettcare.app.sharedprefs.di.sharedPrefsModule
import com.pettcare.app.socialwall.data.repository.di.socialWallDataModule
import com.pettcare.app.socialwall.domain.di.socialWallDomainModule
import com.pettcare.app.socialwall.network.di.socialWallNetworkModule
import com.pettcare.app.socialwall.presentation.di.socialWallPresentationModule
import com.pettcare.app.welcome.domain.di.domainWelcomeModule
import com.pettcare.app.welcome.presentation.di.welcomeModule
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
                    domainWelcomeModule,
                    navigationModule,
                    loginPresentationModule,
                    authDomainModule,
                    authDataModule,
                    signInPresentationModule,
                    signInDomainModule,
                    firebaseModule,
                    homeUiModule,
                    homeDomainModule,
                    homeModelModule,
                    homeNetworkModule,
                    bottomNavigationModule,
                    socialWallPresentationModule,
                    socialWallDomainModule,
                    socialWallDataModule,
                    socialWallNetworkModule,
                    profilePresentationModule,
                    profileDomainModule,
                    profileDataModule,
                    profileNetworkModule,
                    createFeaturePresentationModule,
                    createFeatureDomainModule,
                    createFeatureDataModule,
                    createFeatureNetworkModule,
                    chatPresentationModule,
                    chatDomainModule,
                    chatDataModule,
                    chatNetworkModule,
                    networkModule("http://192.168.1.169:8081"),
                    authNetworkModule,
                    sharedPrefsModule,
                ),
            )
        }
    }
}
