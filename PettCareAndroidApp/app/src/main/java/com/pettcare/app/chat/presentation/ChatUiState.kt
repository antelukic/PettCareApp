package com.pettcare.app.chat.presentation

import com.pettcare.app.chat.domain.model.Message

data class ChatUiState(
    val messages: List<Message> = emptyList(),
    val isLoading: Boolean = false,
)
