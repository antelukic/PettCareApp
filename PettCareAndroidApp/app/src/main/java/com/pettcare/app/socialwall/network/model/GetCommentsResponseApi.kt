package com.pettcare.app.socialwall.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetCommentsResponseApi(
    @SerialName("items")
    val items: List<CommentResponseApi>,
)

@Serializable
data class CommentResponseApi(
    @SerialName("id")
    val id: String,
    @SerialName("userId")
    val userId: String,
    @SerialName("postId")
    val postId: String,
    @SerialName("text")
    val text: String,
)
