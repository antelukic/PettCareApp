package com.pettcare.app.socialwall.network

import com.pettcare.app.core.BaseApiResponse
import com.pettcare.app.sharedprefs.SharedPreferences
import com.pettcare.app.socialwall.network.model.SocialWallPostsResponseApi
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.url

internal class SocialWallServiceImpl(
    private val client: HttpClient,
    private val sharedPreferences: SharedPreferences,
) : SocialWallService {

    override suspend fun results(page: Int, userId: String?): BaseApiResponse<SocialWallPostsResponseApi>? =
        kotlin.runCatching {
            client.get {
                url(SOCIAL_POSTS)
                parameter(PAGE_SIZE_PARAM, PAGE_SIZE)
                parameter(PAGE_NUMBER_PARAM, page)
                userId?.let {
                    parameter(USER_ID_PARAM, userId)
                }
                sharedPreferences.getString(SharedPreferences.TOKEN_KEY, null)?.let { token ->
                    header("Authorization", "Bearer $token")
                }
            }.body() as BaseApiResponse<SocialWallPostsResponseApi>
        }.onFailure {
            it.printStackTrace()
        }.getOrNull()

    override suspend fun likePost(postId: String) {
        // implement once backend is ready
    }

    override suspend fun postComment(comment: String) {
        // implement once backend is ready
    }

    companion object {
        private const val SOCIAL_POSTS = "/socialPosts"
        private const val PAGE_SIZE_PARAM = "pageSize"
        private const val PAGE_SIZE = "10"
        private const val PAGE_NUMBER_PARAM = "pageNumber"
        private const val USER_ID_PARAM = "userId"
    }
}
