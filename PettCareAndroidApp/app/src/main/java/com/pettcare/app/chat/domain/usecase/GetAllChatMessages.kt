package com.pettcare.app.chat.domain.usecase

import com.pettcare.app.chat.domain.repository.ChatRepository

class GetAllChatMessages(private val chatRepository: ChatRepository) {

    val results = chatRepository.getAllMessagesResults()

    suspend operator fun invoke(receiverId: String) = chatRepository.getAllMessages(receiverId)
}
