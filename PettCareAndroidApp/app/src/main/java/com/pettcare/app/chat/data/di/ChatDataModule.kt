package com.pettcare.app.chat.data.di

import com.pettcare.app.chat.data.repository.ChatRepositoryImpl
import com.pettcare.app.chat.domain.repository.ChatRepository
import org.koin.dsl.module

val chatDataModule = module {
    single<ChatRepository> { ChatRepositoryImpl(get(), get(), get(), get()) }
}
