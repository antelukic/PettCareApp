package com.pettcare.app.socialwall.presentation

import com.pettcare.app.extensions.EMPTY
import com.pettcare.app.socialwall.domain.model.SocialPostComment
import com.pettcare.app.socialwall.domain.model.SocialWallPost
import com.pettcare.app.socialwall.presentation.comments.PresentableSocialPostComment
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

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
    val commentsToShow: ImmutableList<PresentableSocialPostComment>,
    val creatorId: String,
)

fun List<SocialWallPost>.toPresentableSocialPost() = map {
    PresentableSocialPost(
        id = it.id,
        creatorName = it.creatorName,
        avatarUrl = it.avatarUrl,
        photoUrl = it.photoUrl,
        numOfLikes = it.numOfLikes,
        numOfComments = it.numOfComments,
        text = it.text,
        commentsToShow = it.comments.toPresentableSocialPostComments().toImmutableList(),
        creatorId = it.creatorId,
    )
}.toImmutableList()

fun List<SocialPostComment>.toPresentableSocialPostComments() = map { comment ->
    PresentableSocialPostComment(
        id = comment.id,
        name = comment.name,
        avatarUrl = comment.avatarUrl,
        text = comment.text,
    )
}
