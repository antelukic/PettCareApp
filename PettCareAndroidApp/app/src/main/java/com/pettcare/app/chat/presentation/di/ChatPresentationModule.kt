package com.pettcare.app.chat.presentation.di

import com.pettcare.app.chat.presentation.ChatViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val chatPresentationModule = module {
    viewModel { ChatViewModel(get(), get(), get(), get(), get()) }
}
