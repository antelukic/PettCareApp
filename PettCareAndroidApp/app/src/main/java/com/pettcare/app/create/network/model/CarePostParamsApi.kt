package com.pettcare.app.create.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class CarePostParamsApi(
    @SerialName("description")
    val description: String,
    @SerialName("lat")
    val lat: Double,
    @SerialName("lon")
    val lon: Double,
    @SerialName("address")
    val address: String,
    @SerialName("id")
    val id: String = UUID.randomUUID().toString(),
    @SerialName("price")
    val price: String?,
    @SerialName("photoUrl")
    val photoUrl: String?,
    @SerialName("photoId")
    val photoId: String?,
)
