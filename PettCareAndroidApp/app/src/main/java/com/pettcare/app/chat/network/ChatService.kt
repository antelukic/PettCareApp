package com.pettcare.app.chat.network

import com.pettcare.app.chat.network.model.GetChatIdRequestApi
import com.pettcare.app.chat.network.model.GetChatIdResponseApi
import com.pettcare.app.chat.network.model.MessageApi
import com.pettcare.app.chat.network.model.UserChat
import com.pettcare.app.core.BaseApiResponse

interface ChatService {

    suspend fun getAllUserChats(id: String): BaseApiResponse<List<UserChat>>?

    suspend fun getChatId(request: GetChatIdRequestApi): BaseApiResponse<GetChatIdResponseApi>?

    suspend fun getMessagesByChatID(chatId: String): BaseApiResponse<List<MessageApi>>?
}
