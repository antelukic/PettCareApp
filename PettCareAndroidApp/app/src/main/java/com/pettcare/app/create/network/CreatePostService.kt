package com.pettcare.app.create.network

import com.pettcare.app.core.BaseApiResponse
import com.pettcare.app.create.network.model.CreateCarePostRequestApi
import com.pettcare.app.create.network.model.CreateCarePostResponseApi
import com.pettcare.app.create.network.model.CreateSocialPostRequestApi
import com.pettcare.app.create.network.model.CreateSocialPostResponseApi

interface CreatePostService {

    suspend fun createCarePost(params: CreateCarePostRequestApi): BaseApiResponse<CreateCarePostResponseApi>?

    suspend fun createSocialPost(params: CreateSocialPostRequestApi): BaseApiResponse<CreateSocialPostResponseApi>?
}
