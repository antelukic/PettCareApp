package com.pettcare.app.socialwall.presentation.di

import com.pettcare.app.socialwall.presentation.SocialWallViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val socialWallPresentationModule = module {
    viewModel { SocialWallViewModel(get(), get(), get(), get()) }
}
