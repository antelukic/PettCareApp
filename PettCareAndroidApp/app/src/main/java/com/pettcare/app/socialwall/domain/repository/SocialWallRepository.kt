package com.pettcare.app.socialwall.domain.repository

import com.pettcare.app.core.BaseResponse
import com.pettcare.app.socialwall.domain.model.SocialPostComment
import com.pettcare.app.socialwall.domain.model.SocialWallPost
import kotlinx.coroutines.flow.Flow

interface SocialWallRepository {

    suspend fun publishPage(page: Int, userId: String? = null)

    fun results(): Flow<BaseResponse<List<SocialWallPost>>>

    suspend fun likePost(postId: String)

    suspend fun postComment(postId: String, comment: String)

    fun likePostResults(): Flow<BaseResponse<Boolean>>

    fun postCommentResults(): Flow<BaseResponse<Boolean>>

    suspend fun getComments(postId: String)

    fun getCommentsResults(): Flow<BaseResponse<List<SocialPostComment>>>
}
