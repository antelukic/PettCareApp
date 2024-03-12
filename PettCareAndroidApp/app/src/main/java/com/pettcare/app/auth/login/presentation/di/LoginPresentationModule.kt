package com.pettcare.app.auth.login.presentation.di

import com.pettcare.app.auth.login.presentation.LoginViewModel
import com.pettcare.app.auth.login.presentation.translations.LoginTranslations
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginPresentationModule = module {

    factory { LoginTranslations(get()) }
    viewModel { LoginViewModel(get(), get(), get()) }
}
