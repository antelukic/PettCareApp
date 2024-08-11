package com.pettcare.app.profile.data.repository

import com.pettcare.app.core.BaseResponse
import com.pettcare.app.extensions.loadingOnStart
import com.pettcare.app.home.network.response.UserResponseApi
import com.pettcare.app.profile.domain.model.ProfileData
import com.pettcare.app.profile.domain.repository.ProfileRepository
import com.pettcare.app.profile.network.UserService
import com.pettcare.app.sharedprefs.SharedPreferences
import com.pettcare.app.socialwall.domain.model.SocialPostComment
import com.pettcare.app.socialwall.network.SocialWallService
import com.pettcare.app.socialwall.network.model.SocialWallPostResponseApi
import com.pettcare.app.socialwall.network.model.toDomain
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.mapLatest

@OptIn(ExperimentalCoroutinesApi::class)
internal class ProfileRepositoryImpl(
    private val userService: UserService,
    private val socialPostService: SocialWallService,
    private val sharedPreferences: SharedPreferences,
) : ProfileRepository {

    private val idWithPagePublisher = MutableSharedFlow<Pair<Int, String>>(replay = 1)

    private val profileIdResults = idWithPagePublisher.mapLatest { (page, id) ->
        val userId = if (id.isBlank() || id == "{userId}") {
            sharedPreferences.getString(SharedPreferences.ID_KEY, null)
        } else {
            id
        }
        socialPostService.results(page, userId) to getProfileInformation(userId.orEmpty())
    }

    override fun results(): Flow<BaseResponse<ProfileData>> =
        profileIdResults.mapLatest { (posts, profileApi) ->
            if (posts?.data != null && profileApi is BaseResponse.Success) {
                BaseResponse.Success(buildProfileData(profileApi.data, getCommentsPerPosts(posts.data.items)))
            } else {
                BaseResponse.Error.Network
            }
        }.loadingOnStart()

    private suspend fun getCommentsPerPosts(
        posts: List<SocialWallPostResponseApi>,
    ): List<Pair<SocialWallPostResponseApi, List<SocialPostComment>>> {
        val comments = posts.mapNotNull { socialPostService.getComments(it.id)?.data }
        val userInformations = comments.mapNotNull {
            userService.getUserById(it.items.firstOrNull()?.userId.orEmpty())?.data
        }
        return comments.mapNotNull { comment ->
            if (comment.items.isEmpty()) {
                return posts.map {
                    it to emptyList()
                }
            }
            val userInfo =
                userInformations.find { it.id == comment.items.firstOrNull()?.userId } ?: return@mapNotNull null
            val post = posts.find { it.id == comment.items.firstOrNull()?.postId } ?: return@mapNotNull null
            val socialPostComments = comment.items.map {
                SocialPostComment(
                    id = it.id,
                    name = userInfo.name,
                    avatarUrl = userInfo.photoUrl,
                    text = it.text,
                )
            }
            post to socialPostComments
        }
    }

    override suspend fun getProfileById(page: Int, id: String) {
        idWithPagePublisher.emit(page to id)
    }

    private suspend fun getProfileInformation(id: String): BaseResponse<UserResponseApi> =
        userService.getUserById(id)?.data?.let { response ->
            BaseResponse.Success(response)
        } ?: BaseResponse.Error.Network

    private fun buildProfileData(
        profileApi: UserResponseApi,
        posts: List<Pair<SocialWallPostResponseApi, List<SocialPostComment>>>,
    ) = ProfileData(
        id = profileApi.id,
        name = profileApi.name,
        email = profileApi.email,
        photoUrl = profileApi.photoUrl,
        gender = profileApi.gender,
        dateOfBirth = profileApi.dateOfBirth,
        posts = posts.map { (post, comments) ->
            post.toDomain(profileApi, comments)
        },
    )
}
