package com.pettcare.app.create.domain.usecase

import com.pettcare.app.create.domain.model.CarePostParams
import com.pettcare.app.create.domain.repository.CreatePostRepository

class CreateCarePost(private val createPostRepository: CreatePostRepository) {

    suspend operator fun invoke(params: CarePostParams) = createPostRepository.createCarePost(params)
}
