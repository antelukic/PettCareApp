package com.pettcare.app.chat.domain.usecase

import com.pettcare.app.chat.domain.repository.ChatRepository
import com.pettcare.app.core.BaseResponse
import kotlinx.coroutines.flow.Flow

class InitSession(private val chatRepository: ChatRepository) {

    suspend operator fun invoke(userId: String): BaseResponse<Unit> = chatRepository.initSession(userId)
}
