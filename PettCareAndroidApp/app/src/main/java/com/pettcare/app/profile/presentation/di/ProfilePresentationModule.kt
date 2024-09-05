package com.pettcare.app.profile.presentation.di

import com.pettcare.app.profile.presentation.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val profilePresentationModule = module {
    viewModel { parameters ->
        ProfileViewModel(
            getProfileData = get(),
            likeSocialPost = get(),
            postComment = get(),
            id = parameters.get(),
            router = get(),
            signOut = get(),
            sharedPreferences = get(),
        )
    }
}
