package com.pettcare.app.chat.network

import com.pettcare.app.chat.network.model.GetChatIdRequestApi
import com.pettcare.app.chat.network.model.GetChatIdResponseApi
import com.pettcare.app.chat.network.model.MessageApi
import com.pettcare.app.chat.network.model.UserChat
import com.pettcare.app.core.BaseApiResponse
import com.pettcare.app.sharedprefs.SharedPreferences
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url

class ChatServiceImpl(
    private val client: HttpClient,
    private val sharedPreferences: SharedPreferences,
) : ChatService {

    override suspend fun getAllUserChats(id: String): BaseApiResponse<List<UserChat>>? =
        kotlin.runCatching {
            client.get {
                url(CHATS)
                sharedPreferences.getString(SharedPreferences.TOKEN_KEY, null)?.let { token ->
                    header("Authorization", "Bearer $token")
                }
                parameter(ID_PARAM, id)
            }.body() as BaseApiResponse<List<UserChat>>
        }.onFailure {
            it.printStackTrace()
        }.getOrNull()

    override suspend fun getChatId(request: GetChatIdRequestApi): BaseApiResponse<GetChatIdResponseApi>? =
        kotlin.runCatching {
            client.post {
                url(CHAT)
                setBody(request)
                sharedPreferences.getString(SharedPreferences.TOKEN_KEY, null)?.let { token ->
                    header("Authorization", "Bearer $token")
                }
            }.body() as BaseApiResponse<GetChatIdResponseApi>
        }.onFailure {
            it.printStackTrace()
        }.getOrNull()

    override suspend fun getMessagesByChatID(chatId: String): BaseApiResponse<List<MessageApi>>? =
        kotlin.runCatching {
            client.get {
                url(MESSAGES)
                sharedPreferences.getString(SharedPreferences.TOKEN_KEY, null)?.let { token ->
                    header("Authorization", "Bearer $token")
                }
                parameter(CHAT_ID_PARAM, chatId)
            }.body() as BaseApiResponse<List<MessageApi>>
        }.onFailure {
            it.printStackTrace()
        }.getOrNull()

    companion object {
        private const val CHATS = "/chats"
        private const val CHAT = "/chat"
        private const val MESSAGES = "/messages"
        private const val CHAT_ID_PARAM = "chatId"
        private const val ID_PARAM = "id"
    }
}
