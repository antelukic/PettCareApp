package com.pettcare.app.auth.network.model.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestApi(
    val email: String,
    val password: String,
)
