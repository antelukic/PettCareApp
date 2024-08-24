package com.pettcare.app.socialwall.network.model

import com.pettcare.app.home.network.response.UserResponseApi
import com.pettcare.app.socialwall.domain.model.SocialWallPost
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

fun SocialWallPostResponseApi.toDomain(userInfo: UserResponseApi) = SocialWallPost(
    id = id,
    creatorName = userInfo.name,
    avatarUrl = userInfo.photoUrl,
    photoUrl = photoUrl,
    numOfLikes = "30",
    numOfComments = "20",
    text = text,
    comments = listOf(),
    creatorId = userInfo.id,
)
