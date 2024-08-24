package com.pettcare.app.socialwall.network

import com.pettcare.app.core.BaseApiResponse
import com.pettcare.app.socialwall.network.model.SocialWallPostsResponseApi

interface SocialWallService {

    suspend fun results(page: Int, userId: String? = null): BaseApiResponse<SocialWallPostsResponseApi>?

    suspend fun likePost(postId: String)

    suspend fun postComment(comment: String)
}
