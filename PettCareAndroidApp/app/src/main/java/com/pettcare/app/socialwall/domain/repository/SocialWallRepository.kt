package com.pettcare.app.socialwall.domain.repository

import com.pettcare.app.core.BaseResponse
import com.pettcare.app.socialwall.domain.model.SocialWallPost
import kotlinx.coroutines.flow.Flow

interface SocialWallRepository {

    suspend fun publishPage(page: Int)

    fun results(): Flow<BaseResponse<List<SocialWallPost>>>

    suspend fun likePost(postId: String)

    suspend fun postComment(comment: String)

    suspend fun getPostsById(id: String): Flow<BaseResponse<List<SocialWallPost>>>
}
