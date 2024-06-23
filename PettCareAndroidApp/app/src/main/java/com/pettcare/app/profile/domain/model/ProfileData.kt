package com.pettcare.app.profile.domain.model

import com.pettcare.app.socialwall.domain.model.SocialWallPost

data class ProfileData(
    val id: String,
    val name: String,
    val email: String,
    val photoUrl: String?,
    val gender: String,
    val dateOfBirth: String,
    val posts: List<SocialWallPost>,
)
