package com.pettcare.app.create.network.model

import com.pettcare.app.socialwall.network.model.SocialWallPostResponseApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateSocialPostResponseApi(
    @SerialName("response")
    val response: SocialWallPostResponseApi,
)
