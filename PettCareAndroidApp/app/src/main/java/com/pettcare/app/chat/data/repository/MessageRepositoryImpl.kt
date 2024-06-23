package com.pettcare.app.chat.data.repository

import com.pettcare.app.chat.data.mapper.toMessage
import com.pettcare.app.chat.domain.model.Message
import com.pettcare.app.chat.domain.repository.MessageRepository
import com.pettcare.app.chat.network.MessageService
import com.pettcare.app.core.BaseResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class MessageRepositoryImpl(private val messageService: MessageService) : MessageRepository {

    override suspend fun getAllMessages(): Flow<BaseResponse<List<Message>>> = flow {
        val response = messageService.getAllMessages()
        if (response == null) {
            emit(BaseResponse.Error.Network)
        } else {
            emit(BaseResponse.Success(response.map { it.toMessage() }))
        }
    }
}
