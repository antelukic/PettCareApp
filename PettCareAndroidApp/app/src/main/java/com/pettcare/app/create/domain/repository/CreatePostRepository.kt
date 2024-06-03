package com.pettcare.app.create.domain.repository

import com.pettcare.app.create.domain.model.CarePostParams
import com.pettcare.app.create.domain.model.SocialPostParams

interface CreatePostRepository {

    suspend fun createSocialPost(params: SocialPostParams)

    suspend fun createCarePost(params: CarePostParams)
}
