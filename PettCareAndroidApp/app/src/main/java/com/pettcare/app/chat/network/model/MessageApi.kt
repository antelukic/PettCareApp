package com.pettcare.app.chat.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageApi(
    @SerialName("text")
    val text: String,
    @SerialName("dateTime")
    val date: String,
    @SerialName("senderId")
    val senderId: String,
    @SerialName("id")
    val id: String,
)
