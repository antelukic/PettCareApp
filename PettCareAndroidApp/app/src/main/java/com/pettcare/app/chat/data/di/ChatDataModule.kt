package com.pettcare.app.chat.data.di

import com.pettcare.app.chat.data.repository.ChatRepositoryImpl
import com.pettcare.app.chat.data.repository.MessageRepositoryImpl
import com.pettcare.app.chat.domain.repository.ChatRepository
import com.pettcare.app.chat.domain.repository.MessageRepository
import org.koin.dsl.module

val chatDataModule = module {
    factory<ChatRepository> { ChatRepositoryImpl(get()) }
    factory<MessageRepository> { MessageRepositoryImpl(get()) }
}
