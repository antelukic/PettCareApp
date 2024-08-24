package com.pettcare.app.chat.domain.repository

import com.pettcare.app.chat.domain.model.Message
import com.pettcare.app.core.BaseResponse
import com.pettcare.app.profile.domain.model.ProfileData
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    suspend fun getMessages(): Flow<BaseResponse<Message>>

    suspend fun getAllMessages(userId: String)

    fun getAllMessagesResults(): Flow<BaseResponse<List<Message>>>

    suspend fun sendMessage(message: String)

    suspend fun closeSession()

    suspend fun initSession(userId: String): BaseResponse<Unit>

    suspend fun searchUsers(query: String)

    fun searchUserResults(): Flow<BaseResponse<List<ProfileData>>>
}
