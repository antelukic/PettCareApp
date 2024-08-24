package com.pettcare.app.create.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateCarePostRequestApi(
    @SerialName("description")
    val description: String,
    @SerialName("lat")
    val lat: Double,
    @SerialName("lon")
    val lon: Double,
    @SerialName("address")
    val address: String,
    @SerialName("creatorId")
    val creatorId: String = "",
    @SerialName("price")
    val price: String?,
    @SerialName("photoUrl")
    val photoUrl: String?,
    @SerialName("photoId")
    val photoId: String?,
)
