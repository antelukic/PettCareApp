package com.pettcare.app.socialwall.network

import com.pettcare.app.core.BaseApiResponse
import com.pettcare.app.socialwall.network.model.AddCommentRequestApi
import com.pettcare.app.socialwall.network.model.AddCommentResponseApi
import com.pettcare.app.socialwall.network.model.GetCommentsResponseApi
import com.pettcare.app.socialwall.network.model.LikePostRequestApi
import com.pettcare.app.socialwall.network.model.SocialWallPostsResponseApi

interface SocialWallService {

    suspend fun results(page: Int, userId: String? = null): BaseApiResponse<SocialWallPostsResponseApi>?

    suspend fun likePost(request: LikePostRequestApi): BaseApiResponse<Boolean>?

    suspend fun postComment(request: AddCommentRequestApi): BaseApiResponse<AddCommentResponseApi>?

    suspend fun getComments(postId: String): BaseApiResponse<GetCommentsResponseApi>?
}
