@file:Suppress("ImportOrdering", "ktlint:import-ordering")

package com.pettcare.app.chat.network

import com.pettcare.app.chat.network.model.InitSessionRequestApi
import com.pettcare.app.chat.network.model.MessageApi
import com.pettcare.app.core.BaseResponse
import com.pettcare.app.core.network.MyTrustManager
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.websocket.DefaultClientWebSocketSession
import io.ktor.client.features.websocket.WebSockets
import io.ktor.client.features.websocket.webSocketSession
import io.ktor.client.request.url
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.close
import io.ktor.http.cio.websocket.readText
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive

internal class ChatSocketServiceImpl : ChatSocketService {

    private val client = HttpClient(CIO) {
        install(Logging)
        install(WebSockets)
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }

        engine {
            https {
                trustManager = MyTrustManager(this)
            }
        }
    }

    private var socket: DefaultClientWebSocketSession? = null

    private var retry: Boolean = true
    private var chatId = ""
    private var senderId = ""

    @Suppress("MaximumLineLength")
    override suspend fun initSession(request: InitSessionRequestApi): BaseResponse<Unit> {
        chatId = request.chatId
        senderId = request.senderId
        return runCatching {
            socket = client.webSocketSession {
                url("ws://literate-waddle-vw4p69gqrqqfxw7w-8081.app.github.dev/message?chatId=${request.chatId}&userId=${request.senderId}")
            }
            if (socket?.isActive == true) {
                BaseResponse.Success(Unit)
            } else {
                if (retry) {
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
                    val message = (it as? Frame.Text)?.readText() ?: ""
                    MessageApi(
                        text = message,
                        senderId = senderId,
                        id = chatId,
                        date = getCurrentDateTime(),
                    )
                } ?: flow { }
        }.onFailure {
            it.printStackTrace()
        }.getOrElse {
            flow { emit(null) }
        }
    }

    private fun getCurrentDateTime(): String {
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
        return currentDateTime.format(formatter)
    }

    override suspend fun closeSession() {
        socket?.close()
    }
}
