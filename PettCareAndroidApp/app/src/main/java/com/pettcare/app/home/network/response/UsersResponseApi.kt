package com.pettcare.app.home.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UsersResponseApi(
    @SerialName("items")
    val items: List<UserResponseApi>,
)
