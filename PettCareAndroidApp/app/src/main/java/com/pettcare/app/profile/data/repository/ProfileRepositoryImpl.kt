package com.pettcare.app.profile.data.repository

import com.pettcare.app.core.BaseResponse
import com.pettcare.app.extensions.loadingOnStart
import com.pettcare.app.profile.domain.model.ProfileData
import com.pettcare.app.profile.domain.repository.ProfileRepository
import com.pettcare.app.profile.network.ApiProfile
import com.pettcare.app.profile.network.model.ProfileApi
import com.pettcare.app.socialwall.domain.model.SocialWallPost
import com.pettcare.app.socialwall.domain.repository.SocialWallRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow

internal class ProfileRepositoryImpl(
    private val apiProfile: ApiProfile,
    private val socialWallRepository: SocialWallRepository,
) : ProfileRepository {

    override suspend fun getProfileById(id: String): Flow<BaseResponse<ProfileData>> = combine(
        getProfileInformation(id),
        socialWallRepository.getPostsById(id),
    ) { profileApi, posts ->
        if (posts is BaseResponse.Success && profileApi is BaseResponse.Success) {
            BaseResponse.Success(buildProfileData(profileApi.data, posts.data))
        } else {
            BaseResponse.Error.Other
        }
    }.loadingOnStart()

    private suspend fun getProfileInformation(id: String) = flow {
        runCatching {
            apiProfile.getProfileById(id)
        }.onSuccess {
            emit(BaseResponse.Success(it))
        }.onFailure {
            if (it is Exception) {
                emit(BaseResponse.Error.Network(it))
            } else {
                emit(BaseResponse.Error.Other)
            }
        }
    }

    private fun buildProfileData(profileApi: ProfileApi, posts: List<SocialWallPost>) = ProfileData(
        id = profileApi.id,
        name = profileApi.name,
        email = profileApi.email,
        photoUrl = profileApi.photoUrl,
        gender = profileApi.gender,
        dateOfBirth = profileApi.dateOfBirth,
        posts = posts,
    )
}
