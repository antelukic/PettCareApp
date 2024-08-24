package com.pettcare.app.chat.data.repository

import com.pettcare.app.chat.data.mapper.toMessage
import com.pettcare.app.chat.domain.model.Message
import com.pettcare.app.chat.domain.repository.ChatRepository
import com.pettcare.app.chat.network.ChatService
import com.pettcare.app.chat.network.ChatSocketService
import com.pettcare.app.chat.network.model.GetChatIdRequestApi
import com.pettcare.app.chat.network.model.InitSessionRequestApi
import com.pettcare.app.chat.network.model.UserChat
import com.pettcare.app.core.BaseResponse
import com.pettcare.app.home.network.response.UserResponseApi
import com.pettcare.app.profile.domain.model.ProfileData
import com.pettcare.app.profile.network.UserService
import com.pettcare.app.sharedprefs.SharedPreferences
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest
import kotlin.time.Duration.Companion.milliseconds

internal class ChatRepositoryImpl(
    private val chatSocketService: ChatSocketService,
    private val chatService: ChatService,
    private val userService: UserService,
    private val sharedPreferences: SharedPreferences,
) : ChatRepository {

    private val queryOrIdPublisher = MutableSharedFlow<String>(replay = 1)
    private val idPublisher = MutableSharedFlow<String>(replay = 1)

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getMessages(): Flow<BaseResponse<Message>> = chatSocketService.observeMessages()
        .mapLatest {
            if (it == null) {
                BaseResponse.Error.Network
            } else {
                BaseResponse.Success(it.toMessage())
            }
        }

    override suspend fun getAllMessages(userId: String) {
        idPublisher.emit(userId)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getAllMessagesResults(): Flow<BaseResponse<List<Message>>> = idPublisher
        .mapLatest(::getAllUserMessages)

    override suspend fun sendMessage(message: String) = chatSocketService.sendMessage(message)

    override suspend fun closeSession() = chatSocketService.closeSession()

    override suspend fun initSession(userId: String): BaseResponse<Unit> {
        val currentUserId = sharedPreferences.getString(SharedPreferences.ID_KEY, null)
            ?: return BaseResponse.Error.Other
        val chatId = chatService.getChatId(GetChatIdRequestApi(currentUserId, userId))
            ?: return BaseResponse.Error.Other
        return chatSocketService.initSession(
            InitSessionRequestApi(
                senderId = userId,
                text = "",
                chatId = chatId.data?.id.orEmpty(),
            )
        )
    }

    override suspend fun searchUsers(query: String) {
        queryOrIdPublisher.emit(query)
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    override fun searchUserResults(): Flow<BaseResponse<List<ProfileData>>> = queryOrIdPublisher
        .debounce(200.milliseconds)
        .mapLatest { query ->
            if (query.isBlank()) {
                val userId = sharedPreferences.getString(SharedPreferences.ID_KEY, null)
                    ?: return@mapLatest BaseResponse.Error.Other
                val response = chatService.getAllUserChats(userId)
                if (response?.data != null) {
                    BaseResponse.Success(getProfileData(response.data))
                } else {
                    BaseResponse.Error.Network
                }
            } else {
                val response = userService.searchUsers(query)
                if (response?.data != null) {
                    BaseResponse.Success(response.data.items.toProfileData())
                } else {
                    BaseResponse.Error.Network
                }
            }
        }

    private fun List<UserResponseApi>.toProfileData() = map { user ->
        user.toProfileData()
    }

    private fun UserResponseApi.toProfileData() =
        ProfileData(
            id = id,
            name = name,
            email = email,
            photoUrl = photoUrl,
            gender = gender,
            dateOfBirth = dateOfBirth,
            posts = emptyList(),
        )

    private suspend fun getProfileData(chats: List<UserChat>): List<ProfileData> {
        val userId = sharedPreferences.getString(SharedPreferences.ID_KEY, null) ?: return emptyList()
        return chats.mapNotNull { userChat ->
            if (userChat.firstUserId != userId) {
                val response = userService.getUserById(userChat.firstUserId)?.data ?: return@mapNotNull null
                response.toProfileData()
            } else {
                val response = userService.getUserById(userChat.secondUserId)?.data ?: return@mapNotNull null
                response.toProfileData()
            }
        }
    }

    private suspend fun getAllUserMessages(receiverId: String): BaseResponse<List<Message>> {
        val currentUserId = sharedPreferences.getString(SharedPreferences.ID_KEY, null)
            ?: return BaseResponse.Error.Other
        val chatId = chatService.getChatId(GetChatIdRequestApi(currentUserId, receiverId))?.data?.id
            ?: return BaseResponse.Error.Network
        val messages = chatService.getMessagesByChatID(chatId)?.data?.map { it.toMessage() }
            ?: return BaseResponse.Error.Network
        return BaseResponse.Success(messages)
    }
}
