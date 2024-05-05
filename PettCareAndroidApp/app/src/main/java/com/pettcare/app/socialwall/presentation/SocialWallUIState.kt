package com.pettcare.app.socialwall.presentation

import com.pettcare.app.extensions.EMPTY
import com.pettcare.app.socialwall.domain.model.SocialPostComment
import com.pettcare.app.socialwall.domain.model.SocialWallPost
import com.pettcare.app.socialwall.presentation.comments.PresentableSocialPostComment
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

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

fun List<SocialWallPost>.toPresentableSocialPost() = map {
    PresentableSocialPost(
        id = it.id,
        creatorName = it.creatorName,
        avatarUrl = it.avatarUrl,
        photoUrl = it.photoUrl,
        numOfLikes = it.numOfLikes,
        numOfComments = it.numOfComments,
        text = it.text,
        comments = it.comments.map { comment -> comment.toPresentableSocialPostComment() }.toImmutableList(),
    )
}.toImmutableList()

fun SocialPostComment.toPresentableSocialPostComment() = PresentableSocialPostComment(
    id = id,
    name = name,
    avatarUrl = avatarUrl,
    text = text,
)
