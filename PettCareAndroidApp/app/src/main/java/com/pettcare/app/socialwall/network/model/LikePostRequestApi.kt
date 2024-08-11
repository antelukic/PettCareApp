package com.pettcare.app.socialwall.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LikePostRequestApi(
    @SerialName("id")
    val id: String,
)
