package com.pettcare.app.home.presentation.di

import com.pettcare.app.home.presentation.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeUiModule = module {
    viewModel { HomeViewModel(get(), get()) }
}
