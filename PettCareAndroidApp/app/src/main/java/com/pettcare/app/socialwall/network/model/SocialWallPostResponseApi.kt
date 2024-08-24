package com.pettcare.app.socialwall.network.model

import com.pettcare.app.home.network.response.UserResponseApi
import com.pettcare.app.socialwall.domain.model.SocialPostComment
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
    @SerialName("numOfLikes")
    val numOfLikes: Int,
)

fun SocialWallPostResponseApi.toDomain(userInfo: UserResponseApi, comments: List<SocialPostComment>) = SocialWallPost(
    id = id,
    creatorName = userInfo.name,
    avatarUrl = userInfo.photoUrl,
    photoUrl = photoUrl,
    numOfLikes = numOfLikes.toString(),
    numOfComments = comments.count().toString(),
    text = text,
    comments = comments,
    creatorId = userInfo.id,
)
