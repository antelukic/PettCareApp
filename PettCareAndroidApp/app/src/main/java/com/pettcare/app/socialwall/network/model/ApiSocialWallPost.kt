package com.pettcare.app.socialwall.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiSocialWallPost(
    @SerialName("id")
    val id: String,
    @SerialName("creatorName")
    val creatorName: String,
    @SerialName("avatarUrl")
    val avatarUrl: String?,
    @SerialName("photoUrl")
    val photoUrl: String?,
    @SerialName("numOfLikes")
    val numOfLikes: String,
    @SerialName("numOfComments")
    val numOfComments: String,
    @SerialName("text")
    val text: String?,
    @SerialName("comments")
    val comments: List<ApiSocialWallPostComments>,
)
