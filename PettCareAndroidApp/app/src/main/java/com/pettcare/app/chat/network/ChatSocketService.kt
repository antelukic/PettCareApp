package com.pettcare.app.chat.network

import com.pettcare.app.chat.network.model.InitSessionRequestApi
import com.pettcare.app.chat.network.model.MessageApi
import com.pettcare.app.core.BaseResponse
import kotlinx.coroutines.flow.Flow

interface ChatSocketService {

    suspend fun initSession(request: InitSessionRequestApi): BaseResponse<Unit>

    suspend fun sendMessage(message: String)

    fun observeMessages(): Flow<MessageApi?>

    suspend fun closeSession()

    companion object {
        const val BASE_URL = "ws://192.168.1.169:8081"
    }

    sealed class Endpoints(val url: String) {
        data object ChatSocket : Endpoints("$BASE_URL/message")
    }
}
