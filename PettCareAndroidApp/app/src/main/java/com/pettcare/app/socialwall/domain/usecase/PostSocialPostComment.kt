package com.pettcare.app.socialwall.domain.usecase

import com.pettcare.app.socialwall.domain.repository.SocialWallRepository

class PostSocialPostComment(private val repository: SocialWallRepository) {

    suspend operator fun invoke(value: String) = repository.postComment(value)
}
