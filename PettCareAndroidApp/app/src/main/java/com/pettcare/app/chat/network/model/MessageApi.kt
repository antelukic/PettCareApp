package com.pettcare.app.chat.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageApi(
    @SerialName("text")
    val text: String,
    @SerialName("timestamp")
    val timestamp: Long,
    @SerialName("username")
    val username: String,
    @SerialName("id")
    val id: String,
)
