package com.pettcare.app.chat.network

import com.pettcare.app.chat.network.model.MessageApi

interface MessageService {

    suspend fun getAllMessages(): List<MessageApi>?

    companion object {
        const val BASE_URL = "http://192.168.0.2:8082"
    }

    sealed class Endpoints(val url: String) {
        data object GetAllMessages : Endpoints("$BASE_URL/messages")
    }
}
