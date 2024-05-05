package com.pettcare.app.profile.domain.usecase

import com.pettcare.app.core.BaseResponse
import com.pettcare.app.profile.domain.model.ProfileData
import com.pettcare.app.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow

class GetProfileData(private val profileRepository: ProfileRepository) {

    suspend operator fun invoke(id: String): Flow<BaseResponse<ProfileData>> = profileRepository.getProfileById(id)
}
