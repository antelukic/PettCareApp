package com.pettcare.app.chat.domain.usecase

import com.pettcare.app.chat.domain.repository.ChatRepository

class GetUserChats(private val chatRepository: ChatRepository) {

    val results = chatRepository.searchUserResults()

    suspend operator fun invoke(query: String) = chatRepository.searchUsers(query)
}
