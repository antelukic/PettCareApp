package com.pettcare.app.socialwall.network

import com.pettcare.app.BASE_URL
import com.pettcare.app.core.BaseApiResponse
import com.pettcare.app.sharedprefs.SharedPreferences
import com.pettcare.app.socialwall.network.model.AddCommentRequestApi
import com.pettcare.app.socialwall.network.model.AddCommentResponseApi
import com.pettcare.app.socialwall.network.model.GetCommentsResponseApi
import com.pettcare.app.socialwall.network.model.LikePostRequestApi
import com.pettcare.app.socialwall.network.model.SocialWallPostsResponseApi
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post

internal class SocialWallServiceImpl(
    private val client: HttpClient,
    private val sharedPreferences: SharedPreferences,
) : SocialWallService {

    override suspend fun results(page: Int, userId: String?): BaseApiResponse<SocialWallPostsResponseApi>? =
        kotlin.runCatching {
            client.get<BaseApiResponse<SocialWallPostsResponseApi>>(BASE_URL + SOCIAL_POSTS) {
                parameter(PAGE_SIZE_PARAM, PAGE_SIZE)
                parameter(PAGE_NUMBER_PARAM, page)
                userId?.let {
                    parameter(USER_ID_PARAM, userId)
                }
                authorization(sharedPreferences)
            }
        }.onFailure {
            it.printStackTrace()
        }.getOrNull()

    override suspend fun likePost(request: LikePostRequestApi): BaseApiResponse<Boolean>? = kotlin.runCatching {
        client.post<BaseApiResponse<Boolean>>(BASE_URL + LIKE_POST) {
            authorization(sharedPreferences)
            body = request
        }
    }.onFailure { it.printStackTrace() }
        .getOrNull()

    override suspend fun postComment(request: AddCommentRequestApi): BaseApiResponse<AddCommentResponseApi>? =
        kotlin.runCatching {
            client.post<BaseApiResponse<AddCommentResponseApi>>(BASE_URL + ADD_COMMENT) {
                authorization(sharedPreferences)
                body = request
            }
        }.onFailure { it.printStackTrace() }
            .getOrNull()

    override suspend fun getComments(postId: String): BaseApiResponse<GetCommentsResponseApi>? =
        kotlin.runCatching {
            client.get<BaseApiResponse<GetCommentsResponseApi>>(BASE_URL + COMMENTS) {
                authorization(sharedPreferences)
                parameter(COMMENTS_ID_PARAM, postId)
            }
        }.onFailure { it.printStackTrace() }
            .getOrNull()

    private fun HttpRequestBuilder.authorization(sharedPreferences: SharedPreferences) {
        sharedPreferences.getString(SharedPreferences.TOKEN_KEY, null)?.let { token ->
            header("Authorization", "Bearer $token")
        }
    }

    companion object {
        private const val SOCIAL_POSTS = "/socialPosts"
        private const val PAGE_SIZE_PARAM = "pageSize"
        private const val PAGE_SIZE = "10"
        private const val PAGE_NUMBER_PARAM = "pageNumber"
        private const val USER_ID_PARAM = "userId"
        private const val LIKE_POST = "/socialPost/like"
        private const val ADD_COMMENT = "/comments/add"
        private const val COMMENTS = "/comments"
        private const val COMMENTS_ID_PARAM = "id"
    }
}
