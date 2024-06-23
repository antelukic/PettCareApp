package com.pettcare.app.socialwall.network

import com.pettcare.app.socialwall.network.model.ApiSocialWallPost

interface SocialWallApi {

    suspend fun results(page: Int): List<ApiSocialWallPost>

    suspend fun likePost(postId: String)

    suspend fun postComment(comment: String)

    suspend fun getPostsById(id: String): List<ApiSocialWallPost>
}
