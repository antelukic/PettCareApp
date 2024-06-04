package com.pettcare.app.chat.data.repository

import com.pettcare.app.chat.data.mapper.toMessage
import com.pettcare.app.chat.domain.model.Message
import com.pettcare.app.chat.domain.repository.ChatRepository
import com.pettcare.app.chat.network.ChatSocketService
import com.pettcare.app.core.BaseResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

internal class ChatRepositoryImpl(private val chatSocketService: ChatSocketService) : ChatRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getMessages(): Flow<BaseResponse<Message>> = chatSocketService.observeMessages()
        .mapLatest {
            if (it == null) {
                BaseResponse.Error.Network
            } else {
                BaseResponse.Success(it.toMessage())
            }
        }

    override suspend fun sendMessage(message: String) = chatSocketService.sendMessage(message)

    override suspend fun closeSession() = chatSocketService.closeSession()

    override suspend fun initSession(username: String): BaseResponse<Unit> = chatSocketService.initSession(username)
}
