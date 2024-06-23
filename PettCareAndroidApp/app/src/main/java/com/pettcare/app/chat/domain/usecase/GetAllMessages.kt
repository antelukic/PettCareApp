package com.pettcare.app.chat.domain.usecase

import com.pettcare.app.chat.domain.repository.MessageRepository

class GetAllMessages(private val messageRepository: MessageRepository) {

    suspend operator fun invoke() = messageRepository.getAllMessages()
}
