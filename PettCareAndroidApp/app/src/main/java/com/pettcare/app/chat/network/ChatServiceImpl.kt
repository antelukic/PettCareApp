package com.pettcare.app.chat.network

import com.pettcare.app.BASE_URL
import com.pettcare.app.chat.network.model.GetChatIdRequestApi
import com.pettcare.app.chat.network.model.GetChatIdResponseApi
import com.pettcare.app.chat.network.model.MessageApi
import com.pettcare.app.chat.network.model.UserChat
import com.pettcare.app.core.BaseApiResponse
import com.pettcare.app.sharedprefs.SharedPreferences
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post

class ChatServiceImpl(
    private val client: HttpClient,
    private val sharedPreferences: SharedPreferences,
) : ChatService {

    override suspend fun getAllUserChats(id: String): BaseApiResponse<List<UserChat>>? =
        kotlin.runCatching {
            client.get<BaseApiResponse<List<UserChat>>>(BASE_URL + CHATS) {
                sharedPreferences.getString(SharedPreferences.TOKEN_KEY, null)?.let { token ->
                    header("Authorization", "Bearer $token")
                }
                parameter(ID_PARAM, id)
            }
        }.onFailure {
            it.printStackTrace()
        }.getOrNull()

    override suspend fun getChatId(request: GetChatIdRequestApi): BaseApiResponse<GetChatIdResponseApi>? =
        kotlin.runCatching {
            client.post<BaseApiResponse<GetChatIdResponseApi>>(BASE_URL + CHAT) {
                body = request
                sharedPreferences.getString(SharedPreferences.TOKEN_KEY, null)?.let { token ->
                    header("Authorization", "Bearer $token")
                }
            }
        }.onFailure {
            it.printStackTrace()
        }.getOrNull()

    override suspend fun getMessagesByChatID(chatId: String): BaseApiResponse<List<MessageApi>>? =
        kotlin.runCatching {
            client.get<BaseApiResponse<List<MessageApi>>>(BASE_URL + MESSAGES) {
                sharedPreferences.getString(SharedPreferences.TOKEN_KEY, null)?.let { token ->
                    header("Authorization", "Bearer $token")
                }
                parameter(CHAT_ID_PARAM, chatId)
            }
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
