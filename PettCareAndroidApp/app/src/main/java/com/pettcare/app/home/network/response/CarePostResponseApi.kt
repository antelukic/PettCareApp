package com.pettcare.app.home.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CarePostResponseApi(
    @SerialName("id")
    val id: String,
    @SerialName("photoId")
    val photoUrl: String?,
    @SerialName("price")
    val price: String?,
    @SerialName("description")
    val description: String,
    @SerialName("lat")
    val lat: Double,
    @SerialName("lon")
    val lon: Double,
    @SerialName("address")
    val address: String,
    @SerialName("creatorId")
    val creatorId: String,
)

@Serializable
data class CarePostsResponseApi(
    @SerialName("items")
    val items: List<CarePostResponseApi>,
)
