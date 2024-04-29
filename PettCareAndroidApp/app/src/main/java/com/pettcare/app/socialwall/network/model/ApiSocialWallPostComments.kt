package com.pettcare.app.socialwall.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiSocialWallPostComments(
    @SerialName("id")
    val id: String,
    @SerialName("creatorName")
    val creatorName: String,
    @SerialName("avatarUrl")
    val avatarUrl: String?,
    @SerialName("text")
    val text: String,
)
