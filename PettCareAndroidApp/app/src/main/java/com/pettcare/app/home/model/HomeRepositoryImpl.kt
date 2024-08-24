package com.pettcare.app.home.model

import com.google.android.gms.maps.model.LatLng
import com.pettcare.app.core.BaseApiResponse
import com.pettcare.app.core.BaseResponse
import com.pettcare.app.extensions.loadingOnStart
import com.pettcare.app.home.domain.HomeRepository
import com.pettcare.app.home.domain.model.CarePost
import com.pettcare.app.home.domain.model.HomeData
import com.pettcare.app.home.network.response.CarePostResponseApi
import com.pettcare.app.home.network.response.CarePostsResponseApi
import com.pettcare.app.home.network.response.UserResponseApi
import com.pettcare.app.home.network.service.CarePostService
import com.pettcare.app.profile.network.UserService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onStart

internal class HomeRepositoryImpl(
    private val carePostService: CarePostService,
    private val userService: UserService,
) : HomeRepository {

    private val pagePublisher = MutableSharedFlow<Int>(1)

    override suspend fun publishPage(value: Int) {
        pagePublisher.emit(value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun result(): Flow<BaseResponse<HomeData>> = pagePublisher
        .mapLatest {
            handleResponse(carePostService.carePosts(it.toString()))
        }
        .loadingOnStart()
        .onStart {
            BaseResponse.Loading
        }

    private suspend fun handleResponse(response: BaseApiResponse<CarePostsResponseApi>?): BaseResponse<HomeData> {
        if (response?.data == null) return BaseResponse.Error.Network
        val userInformations = response.data.items.mapNotNull { getUserInformation(it.creatorId) }
        val postsWithUserInfo = response.data.items.mapNotNull { post ->
            userInformations.find { user -> user.id == post.creatorId }?.let { user ->
                post to user
            }
        }
        return BaseResponse.Success(getHomeData(postsWithUserInfo))
    }

    private suspend fun getUserInformation(userId: String): UserResponseApi? = userService.getUserById(userId)?.data

    private fun getHomeData(postsWithUserInfo: List<Pair<CarePostResponseApi, UserResponseApi>>) = HomeData(
        profiles = postsWithUserInfo.map { (post, user) ->
            CarePost(
                id = post.id,
                name = user.name,
                photoUrl = user.photoUrl,
                description = post.description,
                price = post.price,
                location = LatLng(post.lat, post.lon),
                address = post.address,
                postImageUrl = post.photoUrl,
                creatorId = user.id,
            )
        },
    )
}
