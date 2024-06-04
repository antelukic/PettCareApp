package com.pettcare.app.chat.domain.usecase

import com.pettcare.app.chat.domain.repository.ChatRepository

class SendMessage(private val chatRepository: ChatRepository) {

    suspend fun messages() = chatRepository.getMessages()

    suspend operator fun invoke(message: String) = chatRepository.sendMessage(message)
}
