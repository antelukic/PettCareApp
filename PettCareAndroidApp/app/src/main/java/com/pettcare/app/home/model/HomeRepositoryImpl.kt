package com.pettcare.app.home.model

import com.google.android.gms.maps.model.LatLng
import com.pettcare.app.core.BaseResponse
import com.pettcare.app.extensions.loadingOnStart
import com.pettcare.app.home.domain.HomeRepository
import com.pettcare.app.home.domain.model.CarePostProfile
import com.pettcare.app.home.domain.model.HomeData
import com.pettcare.app.home.network.ApiCarePostProfile
import com.pettcare.app.home.network.CarePostProfileApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onStart

private const val INITIAL_PAGE = 0
private const val DEBOUNCE_TIME = 2000L

internal class HomeRepositoryImpl(
    private val carePostProfileApi: CarePostProfileApi,
) : HomeRepository {

    private val pagePublisher = MutableSharedFlow<Int>(1)

    override suspend fun publishPage(value: Int) {
        pagePublisher.emit(value)
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    override fun result(): Flow<BaseResponse<HomeData>> = pagePublisher
        .mapLatest {
            BaseResponse.Success(carePostProfileApi.response().toDomain())
        }.debounce(DEBOUNCE_TIME)
        .loadingOnStart()
        .onStart {
            publishPage(INITIAL_PAGE)
        }

    private fun List<ApiCarePostProfile>.toDomain() = HomeData(
        profiles = map { apiCarePostProfile ->
            CarePostProfile(
                photoUrl = apiCarePostProfile.photoUrl,
                name = apiCarePostProfile.name,
                price = apiCarePostProfile.price,
                description = apiCarePostProfile.description,
                address = apiCarePostProfile.address,
                id = apiCarePostProfile.id,
                location = LatLng(apiCarePostProfile.lat, apiCarePostProfile.lon),
            )
        },
    )
}
