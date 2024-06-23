package com.pettcare.app.socialwall.domain.usecase

import com.pettcare.app.socialwall.domain.repository.SocialWallRepository

class GetSocialWallPost(private val socialWallRepository: SocialWallRepository) {

    suspend fun publishPage(page: Int) = socialWallRepository.publishPage(page)

    fun results() = socialWallRepository.results()
}
