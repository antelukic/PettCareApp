package com.pettcare.app.auth.network.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInRequestApi(
    @SerialName("fullName")
    val fullName: String,
    @SerialName("avatar")
    val avatarUrl: String?,
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String,
    @SerialName("imageId")
    val imageId: String,
    @SerialName("gender")
    val gender: String,
    @SerialName("dateOfBirth")
    val dateOfBirth: String,
)
