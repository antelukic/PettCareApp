package com.pettcare.app.profile.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileApi(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("dateOfBirth")
    val dateOfBirth: String,
    @SerialName("gender")
    val gender: String,
    @SerialName("photoUrl")
    val photoUrl: String?,
    @SerialName("email")
    val email: String,
)
