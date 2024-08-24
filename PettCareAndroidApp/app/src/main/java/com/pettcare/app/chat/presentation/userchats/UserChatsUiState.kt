package com.pettcare.app.chat.presentation.userchats

data class UserChatsUiState(
    val users: List<UserChatUiState> = emptyList(),
    val query: String = "",
)

data class UserChatUiState(
    val photoUrl: String,
    val name: String,
    val id: String,
)
