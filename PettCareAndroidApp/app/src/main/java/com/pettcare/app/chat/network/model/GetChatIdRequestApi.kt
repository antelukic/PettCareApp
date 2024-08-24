package com.pettcare.app.chat.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetChatIdRequestApi(
    @SerialName("firstUserId")
    val firstUserId: String,
    @SerialName("secondUserId")
    val secondUserId: String
)
