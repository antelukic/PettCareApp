package com.pettcare.app.socialwall.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddCommentRequestApi(
    @SerialName("userId")
    val userId: String,
    @SerialName("postId")
    val postId: String,
    @SerialName("text")
    val text: String,
)
