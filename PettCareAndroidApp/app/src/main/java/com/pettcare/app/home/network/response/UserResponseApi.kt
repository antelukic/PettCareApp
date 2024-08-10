package com.pettcare.app.home.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponseApi(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("email")
    val email: String,
    @SerialName("gender")
    val gender: String,
    @SerialName("dateOfBirth")
    val dateOfBirth: String,
    @SerialName("photoUrl")
    val photoUrl: String,
    @SerialName("imageId")
    val imageId: String,
)
