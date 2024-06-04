package com.pettcare.app.chat.domain.usecase

import com.pettcare.app.chat.domain.repository.ChatRepository
import com.pettcare.app.core.BaseResponse

class InitSession(private val chatRepository: ChatRepository) {

    suspend operator fun invoke(username: String): BaseResponse<Unit> = chatRepository.initSession(username)
}
