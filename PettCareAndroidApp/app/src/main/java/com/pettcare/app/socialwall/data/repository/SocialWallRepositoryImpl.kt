package com.pettcare.app.socialwall.data.repository

import com.pettcare.app.core.BaseResponse
import com.pettcare.app.extensions.loadingOnStart
import com.pettcare.app.socialwall.domain.model.SocialPostComment
import com.pettcare.app.socialwall.domain.model.SocialWallPost
import com.pettcare.app.socialwall.domain.repository.SocialWallRepository
import com.pettcare.app.socialwall.network.SocialWallApi
import com.pettcare.app.socialwall.network.model.ApiSocialWallPost
import com.pettcare.app.socialwall.network.model.ApiSocialWallPostComments
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onStart

private const val DEBOUNCE_TIME = 2000L

internal class SocialWallRepositoryImpl(
    private val socialWallApi: SocialWallApi,
) : SocialWallRepository {

    private val pagePublisher = MutableSharedFlow<Int>(1)

    override suspend fun publishPage(page: Int) {
        pagePublisher.emit(page)
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    override fun results(): Flow<BaseResponse<List<SocialWallPost>>> = pagePublisher.mapLatest { page ->
        BaseResponse.Success(socialWallApi.results(page).map { it.toDomain() })
    }.loadingOnStart()
        .debounce(DEBOUNCE_TIME)
        .onStart {
            publishPage(0)
        }

    override suspend fun likePost(postId: String) = socialWallApi.likePost(postId)

    override suspend fun postComment(comment: String) = socialWallApi.postComment(comment)

    private fun ApiSocialWallPost.toDomain() = SocialWallPost(
        id = id,
        creatorName = creatorName,
        avatarUrl = avatarUrl,
        photoUrl = photoUrl,
        numOfLikes = numOfLikes,
        numOfComments = numOfComments,
        text = text,
        comments = comments.map { it.toDomain() },
    )

    private fun ApiSocialWallPostComments.toDomain() = SocialPostComment(
        id = id,
        name = creatorName,
        avatarUrl = avatarUrl,
        text = text,
    )
}
