package com.pettcare.app.socialwall.domain.model

data class SocialPostComment(
    val id: String,
    val name: String,
    val avatarUrl: String?,
    val text: String,
)
