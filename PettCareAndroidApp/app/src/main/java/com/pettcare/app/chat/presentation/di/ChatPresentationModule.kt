package com.pettcare.app.chat.presentation.di

import com.pettcare.app.chat.presentation.ChatViewModel
import com.pettcare.app.chat.presentation.userchats.UserChatsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val chatPresentationModule = module {
    viewModel { parameters ->
        ChatViewModel(
            router = get(),
            userId = parameters.get(),
            getAllMessages = get(),
            sendMessage = get(),
            closeSession = get(),
            initSession = get(),
            getUserInfo = get(),
            getAllChatMessages = get(),
        )
    }
    viewModel { UserChatsViewModel(get(), get()) }
}
