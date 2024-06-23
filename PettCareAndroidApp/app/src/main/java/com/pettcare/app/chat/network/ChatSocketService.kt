package com.pettcare.app.chat.network

import com.pettcare.app.chat.network.model.MessageApi
import com.pettcare.app.core.BaseResponse
import kotlinx.coroutines.flow.Flow

interface ChatSocketService {

    suspend fun initSession(
        username: String,
    ): BaseResponse<Unit>

    suspend fun sendMessage(message: String)

    fun observeMessages(): Flow<MessageApi?>

    suspend fun closeSession()

    companion object {
        const val BASE_URL = "ws://192.168.0.2:8082"
    }

    sealed class Endpoints(val url: String) {
        data object ChatSocket : Endpoints("$BASE_URL/chat-socket")
    }
}
