@file:Suppress("ImportOrdering", "ktlint:import-ordering")

package com.pettcare.app.create.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateSocialPostRequestApi(
    @SerialName("text")
    val text: String?,
    @SerialName("creatorId")
    val creatorId: String = "",
    @SerialName("photoUrl")
    val photoUrl: String?,
    @SerialName("photoId")
    val photoId: String?,
)
