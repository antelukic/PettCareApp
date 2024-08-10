package com.pettcare.app.welcome.presentation.di

import com.pettcare.app.welcome.presentation.WelcomeViewModel
import com.pettcare.app.welcome.presentation.translations.WelcomeResources
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val welcomeModule = module {

    factory { WelcomeResources(androidContext()) }
    viewModel { WelcomeViewModel(get(), get(), get()) }
}
