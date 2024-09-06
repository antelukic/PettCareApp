package com.pettcare.app.chat.domain.di

import com.pettcare.app.chat.domain.usecase.CloseSession
import com.pettcare.app.chat.domain.usecase.GetAllChatMessages
import com.pettcare.app.chat.domain.usecase.GetAllMessages
import com.pettcare.app.chat.domain.usecase.GetUserChats
import com.pettcare.app.chat.domain.usecase.GetUserInfo
import com.pettcare.app.chat.domain.usecase.InitSession
import com.pettcare.app.chat.domain.usecase.SendMessage
import org.koin.dsl.module

val chatDomainModule = module {
    single { GetAllMessages(get()) }
    single { SendMessage(get()) }
    single { CloseSession(get()) }
    single { InitSession(get()) }
    single { GetUserChats(get()) }
    single { GetAllChatMessages(get()) }
    single { GetUserInfo(get()) }
}
