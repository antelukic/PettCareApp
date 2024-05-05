package com.pettcare.app.profile.presentation

import com.pettcare.app.extensions.EMPTY
import com.pettcare.app.socialwall.presentation.PresentableSocialPost
import com.pettcare.app.socialwall.presentation.comments.PresentableSocialPostComment
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class ProfileUiState(
    val name: String = EMPTY,
    val gender: String = EMPTY,
    val photoUrl: String? = EMPTY,
    val dateOfBirth: String = EMPTY,
    val email: String = EMPTY,
    val posts: ImmutableList<PresentableSocialPost> = persistentListOf(),
    val comments: ImmutableList<PresentableSocialPostComment>? = null,
    val comment: String = EMPTY,
)
