package com.pettcare.app.chat.domain.repository

import com.pettcare.app.chat.domain.model.Message
import com.pettcare.app.core.BaseResponse
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    suspend fun getMessages(): Flow<BaseResponse<Message>>

    suspend fun sendMessage(message: String)

    suspend fun closeSession()

    suspend fun initSession(username: String): BaseResponse<Unit>
}
