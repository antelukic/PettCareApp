package com.pettcare.app.socialwall.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddCommentResponseApi(
    @SerialName("isSuccessful")
    val isSuccessful: Boolean,
)
