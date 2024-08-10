package com.pettcare.app.create.domain.repository

import com.pettcare.app.core.BaseResponse
import com.pettcare.app.create.domain.model.CarePostParams
import com.pettcare.app.create.domain.model.SocialPostParams
import kotlinx.coroutines.flow.Flow

interface CreatePostRepository {

    suspend fun createSocialPost(params: SocialPostParams)

    suspend fun createCarePost(params: CarePostParams)

    fun socialPostResults(): Flow<BaseResponse<Boolean>>

    fun carePostResults(): Flow<BaseResponse<Boolean>>
}
