package com.pettcare.app.socialwall.domain.usecase

import com.pettcare.app.socialwall.domain.repository.SocialWallRepository

class PostSocialPostComment(private val repository: SocialWallRepository) {

    val results = repository.postCommentResults()

    suspend operator fun invoke(postId: String, value: String) = repository.postComment(postId, value)
}
