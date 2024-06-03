@file:Suppress("ImportOrdering", "ktlint:import-ordering")

package com.pettcare.app.create.network.model

import java.util.UUID
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SocialPostParamsApi(
    @SerialName("text")
    val text: String?,
    @SerialName("id")
    val id: String = UUID.randomUUID().toString(),
    @SerialName("photoUrl")
    val photoUrl: String?,
    @SerialName("photoId")
    val photoId: String?,
)
