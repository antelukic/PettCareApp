package com.pettcare.app.auth.signin.presentation.di

import com.pettcare.app.auth.signin.presentation.SignInViewModel
import com.pettcare.app.auth.signin.presentation.translation.SignInTranslations
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val signInPresentationModule = module {

    factory { SignInTranslations(get()) }
    viewModel { SignInViewModel(get(), get(), get(), get()) }
}
