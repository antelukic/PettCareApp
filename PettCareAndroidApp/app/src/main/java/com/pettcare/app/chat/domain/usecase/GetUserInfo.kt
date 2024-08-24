package com.pettcare.app.chat.domain.usecase

import com.pettcare.app.profile.domain.repository.ProfileRepository

class GetUserInfo(private val profileRepository: ProfileRepository) {

    val results = profileRepository.results()

    suspend operator fun invoke() = profileRepository.getProfileById(0, "")
}
