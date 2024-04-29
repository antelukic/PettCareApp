package com.pettcare.app.socialwall.presentation

import com.pettcare.app.extensions.EMPTY
import com.pettcare.app.socialwall.presentation.comments.PresentableSocialPostComment
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

private typealias PostId = String

data class SocialWallUIState(
    val posts: ImmutableList<PresentableSocialPost> = persistentListOf(),
    val comments: ImmutableList<PresentableSocialPostComment>? = null,
    val comment: String = EMPTY,
)

data class PresentableSocialPost(
    val id: String,
    val creatorName: String,
    val avatarUrl: String?,
    val photoUrl: String?,
    val numOfLikes: String,
    val numOfComments: String,
    val text: String?,
    val comments: ImmutableList<PresentableSocialPostComment>,
)
