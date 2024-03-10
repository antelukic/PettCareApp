package com.pettcare.app.welcome.di

import com.pettcare.app.welcome.WelcomeViewModel
import com.pettcare.app.welcome.translations.WelcomeResources
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val welcomeModule = module {

    factory { WelcomeResources(androidContext()) }
    viewModel { WelcomeViewModel(get(), get()) }
}
