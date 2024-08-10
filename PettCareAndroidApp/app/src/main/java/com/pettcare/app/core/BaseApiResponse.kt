package com.pettcare.app.core

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseApiResponse<T>(
    @SerialName("data")
    val data: T?,
    @SerialName("message")
    val message: String?,
)
