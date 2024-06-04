package com.pettcare.app.chat.domain.repository

import com.pettcare.app.chat.domain.model.Message
import com.pettcare.app.core.BaseResponse
import kotlinx.coroutines.flow.Flow

interface MessageRepository {

    suspend fun getAllMessages(): Flow<BaseResponse<List<Message>>>
}
