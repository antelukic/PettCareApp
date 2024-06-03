package com.pettcare.app.create.domain.usecase

import com.pettcare.app.create.domain.model.SocialPostParams
import com.pettcare.app.create.domain.repository.CreatePostRepository

class CreateSocialPost(private val createPostRepository: CreatePostRepository) {

    suspend operator fun invoke(paramsApi: SocialPostParams) = createPostRepository.createSocialPost(paramsApi)
}
