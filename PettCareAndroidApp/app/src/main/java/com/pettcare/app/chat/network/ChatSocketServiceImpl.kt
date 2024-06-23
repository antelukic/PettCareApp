package com.pettcare.app.chat.network

import android.util.Log
import com.pettcare.app.chat.network.model.MessageApi
import com.pettcare.app.core.BaseResponse
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.url
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.serialization.json.Json

internal class ChatSocketServiceImpl(
    private val client: HttpClient,
) : ChatSocketService {

    private var socket: WebSocketSession? = null

    override suspend fun initSession(username: String): BaseResponse<Unit> {
        return runCatching {
            socket = client.webSocketSession {
                url("${ChatSocketService.Endpoints.ChatSocket.url}?username=$username")
            }
            if (socket?.isActive == true) {
                BaseResponse.Success(Unit)
            } else {
                BaseResponse.Error.Network
            }
        }.onFailure {
            Log.e("CHAT_SOCKET_SERVICE_IMPL", "initSession: error", it)
        }.getOrElse { BaseResponse.Error.Network }
    }

    override suspend fun sendMessage(message: String) {
        runCatching {
            socket?.send(Frame.Text(message))
        }.onFailure {
            Log.e("CHAT_SOCKET_SERVICE_IMPL", "sendMessage: error", it)
        }
    }

    override fun observeMessages(): Flow<MessageApi?> {
        return runCatching {
            socket?.incoming
                ?.receiveAsFlow()
                ?.filter { it is Frame.Text }
                ?.map {
                    val json = (it as? Frame.Text)?.readText() ?: ""
                    Json.decodeFromString<MessageApi>(json)
                } ?: flow { }
        }.onFailure {
            Log.e("CHAT_SOCKET_SERVICE_IMPL", "observeMessages: error", it)
        }.getOrElse {
            flow { emit(null) }
        }
    }

    override suspend fun closeSession() {
        socket?.close()
    }
}
