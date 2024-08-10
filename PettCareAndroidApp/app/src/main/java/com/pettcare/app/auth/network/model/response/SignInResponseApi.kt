package com.pettcare.app.auth.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInResponseApi(
    @SerialName("authToken")
    val authToken: String,
    @SerialName("id")
    val id: String,
)
