package com.pettcare.app.socialwall.domain.model

data class SocialWallPost(
    val id: String,
    val creatorName: String,
    val avatarUrl: String?,
    val photoUrl: String?,
    val numOfLikes: String,
    val numOfComments: String,
    val text: String?,
    val comments: List<SocialPostComment>,
)
