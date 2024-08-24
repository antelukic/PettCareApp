package com.pettcare.app.auth.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseApi(
    @SerialName("token")
    val authToken: String,
    @SerialName("id")
    val id: String,
)
