package com.pettcare.app.socialwall.presentation.comments

data class PresentableSocialPostComment(
    val id: String,
    val name: String,
    val avatarUrl: String?,
    val text: String,
)
