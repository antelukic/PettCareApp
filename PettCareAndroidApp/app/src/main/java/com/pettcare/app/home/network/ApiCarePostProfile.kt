package com.pettcare.app.home.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiCarePostProfile(
    @SerialName("name")
    val name: String,
    @SerialName("id")
    val id: String,
    @SerialName("photoUrl")
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
)
