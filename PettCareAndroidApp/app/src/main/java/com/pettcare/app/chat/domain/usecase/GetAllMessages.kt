package com.pettcare.app.chat.domain.usecase

import com.pettcare.app.chat.domain.repository.ChatRepository

class GetAllMessages(private val messageRepository: ChatRepository) {

    suspend operator fun invoke() = messageRepository.getMessages()
}
