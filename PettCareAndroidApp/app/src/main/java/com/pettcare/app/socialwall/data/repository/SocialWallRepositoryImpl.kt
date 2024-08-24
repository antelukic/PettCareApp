package com.pettcare.app.socialwall.data.repository

import com.pettcare.app.core.BaseApiResponse
import com.pettcare.app.core.BaseResponse
import com.pettcare.app.extensions.loadingOnStart
import com.pettcare.app.home.network.response.UserResponseApi
import com.pettcare.app.profile.network.UserService
import com.pettcare.app.socialwall.domain.model.SocialWallPost
import com.pettcare.app.socialwall.domain.repository.SocialWallRepository
import com.pettcare.app.socialwall.network.SocialWallService
import com.pettcare.app.socialwall.network.model.SocialWallPostResponseApi
import com.pettcare.app.socialwall.network.model.SocialWallPostsResponseApi
import com.pettcare.app.socialwall.network.model.toDomain
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.mapLatest

internal class SocialWallRepositoryImpl(
    private val socialWallService: SocialWallService,
    private val userService: UserService,
) : SocialWallRepository {

    private val pageAndUserIdPublisher = MutableSharedFlow<Pair<Int, String?>>(1)

    override suspend fun publishPage(page: Int, userId: String?) {
        pageAndUserIdPublisher.emit(page to userId)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun results(): Flow<BaseResponse<List<SocialWallPost>>> =
        pageAndUserIdPublisher.mapLatest { (page, userId) ->
            handleResponse(socialWallService.results(page, userId))
        }.loadingOnStart()

    override suspend fun likePost(postId: String) = socialWallService.likePost(postId)

    override suspend fun postComment(comment: String) = socialWallService.postComment(comment)

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
        return BaseResponse.Success(getSocialWallData(postsWithUserInfo))
    }

    private suspend fun getUserInformation(userId: String): UserResponseApi? = userService.getUserById(userId)?.data

    private fun getSocialWallData(postsWithUserInfo: List<Pair<SocialWallPostResponseApi, UserResponseApi>>) =
        postsWithUserInfo.map { (post, userInfo) ->
            post.toDomain(userInfo)
        }
}
