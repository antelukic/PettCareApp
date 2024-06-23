package com.pettcare.app.create.network

import com.pettcare.app.create.network.model.CarePostParamsApi
import com.pettcare.app.create.network.model.SocialPostParamsApi

interface ApiCreatePost {

    suspend fun createCarePost(params: CarePostParamsApi)

    suspend fun createSocialPost(params: SocialPostParamsApi)
}
