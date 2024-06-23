package com.pettcare.app.profile.domain.repository

import com.pettcare.app.core.BaseResponse
import com.pettcare.app.profile.domain.model.ProfileData
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    suspend fun getProfileById(id: String): Flow<BaseResponse<ProfileData>>
}
