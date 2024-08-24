package com.pettcare.app.chat.network

import com.pettcare.app.chat.network.model.InitSessionRequestApi
import com.pettcare.app.chat.network.model.MessageApi
import com.pettcare.app.core.BaseResponse
import com.pettcare.app.sharedprefs.SharedPreferences
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
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
    private val sharedPreferences: SharedPreferences,
) : ChatSocketService {

    private var socket: WebSocketSession? = null

    private var retry: Boolean = true

    override suspend fun initSession(request: InitSessionRequestApi): BaseResponse<Unit> {
        return runCatching {
            socket = client.webSocketSession {
                url("${ChatSocketService.Endpoints.ChatSocket.url}?chatId=${request.chatId}&senderId=${request.senderId}")
//                sharedPreferences.getString(SharedPreferences.TOKEN_KEY, null)?.let { token ->
//                    header("Authorization", "Bearer $token")
//                }
            }
            if (socket?.isActive == true) {
                BaseResponse.Success(Unit)
            } else {
                if(retry) {
                    retry = false
                    return initSession(request)
                }
                BaseResponse.Error.Network
            }
        }.onFailure {
            it.printStackTrace()
        }.getOrElse { BaseResponse.Error.Network }
    }

    override suspend fun sendMessage(message: String) {
        runCatching {
            socket?.send(Frame.Text(message))
        }.onFailure {
            it.printStackTrace()
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
            it.printStackTrace()
        }.getOrElse {
            flow { emit(null) }
        }
    }

    override suspend fun closeSession() {
        socket?.close()
    }
}
