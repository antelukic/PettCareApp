package com.pettcare.app.chat.domain.usecase

import com.pettcare.app.chat.domain.repository.ChatRepository

class CloseSession(private val chatRepository: ChatRepository) {

    suspend operator fun invoke() = chatRepository.closeSession()
}
