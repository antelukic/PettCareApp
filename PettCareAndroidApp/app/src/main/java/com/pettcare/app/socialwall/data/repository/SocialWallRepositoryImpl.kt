package com.pettcare.app.socialwall.data.repository

import com.pettcare.app.core.BaseApiResponse
import com.pettcare.app.core.BaseResponse
import com.pettcare.app.extensions.loadingOnStart
import com.pettcare.app.home.network.response.UserResponseApi
import com.pettcare.app.profile.network.UserService
import com.pettcare.app.sharedprefs.SharedPreferences
import com.pettcare.app.socialwall.domain.model.SocialPostComment
import com.pettcare.app.socialwall.domain.model.SocialWallPost
import com.pettcare.app.socialwall.domain.repository.SocialWallRepository
import com.pettcare.app.socialwall.network.SocialWallService
import com.pettcare.app.socialwall.network.model.AddCommentRequestApi
import com.pettcare.app.socialwall.network.model.CommentResponseApi
import com.pettcare.app.socialwall.network.model.GetCommentsResponseApi
import com.pettcare.app.socialwall.network.model.LikePostRequestApi
import com.pettcare.app.socialwall.network.model.SocialWallPostResponseApi
import com.pettcare.app.socialwall.network.model.SocialWallPostsResponseApi
import com.pettcare.app.socialwall.network.model.toDomain
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.mapLatest

@OptIn(ExperimentalCoroutinesApi::class)
internal class SocialWallRepositoryImpl(
    private val socialWallService: SocialWallService,
    private val userService: UserService,
    private val sharedPreferences: SharedPreferences,
) : SocialWallRepository {

    private val pageAndUserIdPublisher = MutableSharedFlow<Pair<Int, String?>>(1)

    override suspend fun publishPage(page: Int, userId: String?) {
        pageAndUserIdPublisher.emit(page to userId)
    }

    private val likePostIdPublisher = MutableSharedFlow<String>(replay = 1)
    private val addCommentPublisher = MutableSharedFlow<Pair<String, String>>(replay = 1)
    private val getCommentsPublisher = MutableSharedFlow<String>(replay = 1)

    override fun results(): Flow<BaseResponse<List<SocialWallPost>>> =
        pageAndUserIdPublisher.mapLatest { (page, userId) ->
            handleResponse(socialWallService.results(page, userId))
        }.loadingOnStart()

    override suspend fun getComments(postId: String) {
        getCommentsPublisher.emit(postId)
    }

    override suspend fun likePost(postId: String) {
        likePostIdPublisher.emit(postId)
    }

    override suspend fun postComment(postId: String, comment: String) {
        addCommentPublisher.emit(postId to comment)
    }

    override fun getCommentsResults(): Flow<BaseResponse<List<SocialPostComment>>> = getCommentsPublisher
        .mapLatest { postId ->
            val response = socialWallService.getComments(postId)
            handleCommentResponse(response)
        }

    override fun likePostResults(): Flow<BaseResponse<Boolean>> = likePostIdPublisher
        .mapLatest { postId ->
            val response = socialWallService.likePost(LikePostRequestApi(postId))
            if (response?.data != null) {
                BaseResponse.Success(true)
            } else {
                BaseResponse.Error.Network
            }
        }

    override fun postCommentResults(): Flow<BaseResponse<Boolean>> = addCommentPublisher
        .mapLatest { (postId, comment) ->
            val userId = sharedPreferences.getString(SharedPreferences.ID_KEY, null)
                ?: return@mapLatest BaseResponse.Error.Other
            val response = socialWallService.postComment(
                AddCommentRequestApi(
                    userId = userId,
                    postId = postId,
                    text = comment,
                ),
            )
            if (response?.data != null) {
                BaseResponse.Success(true)
            } else {
                BaseResponse.Error.Network
            }
        }

    private suspend fun handleResponse(
        response: BaseApiResponse<SocialWallPostsResponseApi>?,
    ): BaseResponse<List<SocialWallPost>> {
        if (response?.data == null) return BaseResponse.Error.Network
        val userInformations = response.data.items.mapNotNull { getUserInformation(it.creatorId) }
        val postsWithUserInfo = response.data.items.mapNotNull { post ->
            userInformations.find { user -> user.id == post.creatorId }?.let { user ->
                post to user
            }
        }

        val commentsPerPost = response.data.items.mapNotNull { socialWallService.getComments(it.id)?.data }

        return BaseResponse.Success(getSocialWallData(postsWithUserInfo, commentsPerPost))
    }

    private suspend fun handleCommentResponse(
        response: BaseApiResponse<GetCommentsResponseApi>?,
    ): BaseResponse<List<SocialPostComment>> {
        response?.data ?: return BaseResponse.Error.Network

        val userInformations = response.data.items.mapNotNull { getUserInformation(it.userId) }
        val commentsWithUserInfo = response.data.items.mapNotNull { post ->
            userInformations.find { user -> user.id == post.userId }?.let { user ->
                post to user
            }
        }

        return BaseResponse.Success(
            data = commentsWithUserInfo.map { (comment, user) ->
                SocialPostComment(
                    id = comment.id,
                    name = user.name,
                    avatarUrl = user.photoUrl,
                    text = comment.text,
                )
            },
        )
    }

    private suspend fun getUserInformation(userId: String): UserResponseApi? = userService.getUserById(userId)?.data

    private suspend fun getSocialWallData(
        postsWithUserInfo: List<Pair<SocialWallPostResponseApi, UserResponseApi>>,
        commentsPerPost: List<GetCommentsResponseApi>,
    ) = postsWithUserInfo.map { (post, userInfo) ->
        val comments = commentsPerPost.find { it.items.firstOrNull()?.postId == post.id }?.items.orEmpty()
        post.toDomain(userInfo, comments.toSocialWallComment())
    }

    private suspend fun List<CommentResponseApi>.toSocialWallComment(): List<SocialPostComment> {
        val userInformations = map { getUserInformation(it.userId) }
        val commentsWithUserInformation = mapNotNull { comment ->
            userInformations.find { user -> user?.id == comment.userId }?.let { user ->
                comment to user
            }
        }
        return commentsWithUserInformation.map { (comment, user) ->
            SocialPostComment(
                id = comment.id,
                name = user.name,
                avatarUrl = user.photoUrl,
                text = comment.text,
            )
        }
    }
}
