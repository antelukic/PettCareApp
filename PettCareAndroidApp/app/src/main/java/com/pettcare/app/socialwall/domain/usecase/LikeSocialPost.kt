package com.pettcare.app.socialwall.domain.usecase

import com.pettcare.app.socialwall.domain.repository.SocialWallRepository

class LikeSocialPost(private val repository: SocialWallRepository) {

    val results = repository.likePostResults()

    suspend operator fun invoke(postId: String) = repository.likePost(postId)
}
