package com.pettcare.app.chat.network.model

import kotlinx.serialization.Serializable

@Serializable
data class MessageApi(
    val text: String,
    val timestamp: Long,
    val username: String,
    val id: String,
)
