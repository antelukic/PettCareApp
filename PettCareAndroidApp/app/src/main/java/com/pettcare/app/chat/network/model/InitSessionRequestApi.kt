package com.pettcare.app.chat.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InitSessionRequestApi(
    @SerialName("senderId")
    val senderId: String,
    @SerialName("text")
    val text: String,
    @SerialName("chatId")
    val chatId: String,
)
