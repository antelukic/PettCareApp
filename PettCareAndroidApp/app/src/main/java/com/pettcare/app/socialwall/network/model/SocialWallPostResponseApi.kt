package com.pettcare.app.socialwall.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SocialWallPostsResponseApi(
    @SerialName("items")
    val items: List<SocialWallPostResponseApi>,
)

@Serializable
data class SocialWallPostResponseApi(
    @SerialName("id")
    val id: String,
    @SerialName("creatorId")
    val creatorId: String,
    @SerialName("photoUrl")
    val photoUrl: String?,
    @SerialName("text")
    val text: String?,
)
