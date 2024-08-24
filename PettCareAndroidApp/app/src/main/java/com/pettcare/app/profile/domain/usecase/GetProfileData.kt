package com.pettcare.app.profile.domain.usecase

import com.pettcare.app.profile.domain.repository.ProfileRepository

class GetProfileData(private val profileRepository: ProfileRepository) {

    val results = profileRepository.results()

    suspend operator fun invoke(page: Int, id: String) = profileRepository.getProfileById(page, id)
}
