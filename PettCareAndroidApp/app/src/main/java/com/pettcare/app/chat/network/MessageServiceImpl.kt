package com.pettcare.app.chat.network

import com.pettcare.app.chat.network.model.MessageApi
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url

class MessageServiceImpl(
    private val client: HttpClient,
) : MessageService {

    override suspend fun getAllMessages(): List<MessageApi>? {
        return runCatching {
            client.get {
                url(MessageService.Endpoints.GetAllMessages.url)
            }.body<List<MessageApi>>()
        }.getOrDefault(null)
    }
}
