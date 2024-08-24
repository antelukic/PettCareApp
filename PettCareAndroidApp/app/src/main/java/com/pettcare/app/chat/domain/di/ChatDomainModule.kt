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
    factory { GetAllMessages(get()) }
    factory { SendMessage(get()) }
    factory { CloseSession(get()) }
    factory { InitSession(get()) }
    factory { GetUserChats(get()) }
    factory { GetAllChatMessages(get()) }
    factory { GetUserInfo(get()) }
}
