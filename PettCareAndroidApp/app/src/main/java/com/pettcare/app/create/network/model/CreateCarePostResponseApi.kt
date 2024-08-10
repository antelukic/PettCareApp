package com.pettcare.app.create.network.model

import com.pettcare.app.home.network.response.CarePostResponseApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateCarePostResponseApi(
    @SerialName("response")
    val response: CarePostResponseApi,
)
