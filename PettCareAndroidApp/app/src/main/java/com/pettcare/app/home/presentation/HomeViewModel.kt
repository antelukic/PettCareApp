package com.pettcare.app.home.presentation

import com.pettcare.app.core.BaseResponse
import com.pettcare.app.core.BaseViewModel
import com.pettcare.app.home.domain.GetHomeData
import com.pettcare.app.home.domain.model.CarePost
import com.pettcare.app.home.domain.model.HomeData
import com.pettcare.app.home.presentation.cluster.MapsMarkerCluster
import com.pettcare.app.navigation.Router
import kotlinx.collections.immutable.toImmutableList

class HomeViewModel(
    private val getHomeData: GetHomeData,
    router: Router,
    initialViewState: HomeUiState = HomeUiState(),
) : BaseViewModel<HomeUiState>(router, initialViewState) {

    private var page = 0

    init {
        launchInIO { getHomeData.results().collect(::handleResults) }
        launchInIO { getHomeData.publishPage(page) }
    }

    fun onProfileClicked(id: String) {
        publishNavigationAction {
            it.profile(id)
        }
    }

    fun nextPage() {
        launchInIO {
            page++
            getHomeData.publishPage(page)
        }
    }

    private fun handleResults(response: BaseResponse<HomeData>) {
        when (response) {
            is BaseResponse.Loading -> {}
            is BaseResponse.Error -> {}
            is BaseResponse.Success -> updateUiState { state ->
                state.copy(
                    profiles = (state.profiles + response.data.profiles.toPresentableProfiles()).toImmutableList(),
                    markers = (state.markers + response.data.profiles.toMapMarkers()).toImmutableList(),
                )
            }
        }
    }

    private fun List<CarePost>.toPresentableProfiles() = map {
        PresentableProfiles(
            id = it.id,
            name = it.name,
            photoUrl = it.photoUrl,
            description = it.description,
            price = it.price,
            location = it.location,
            address = it.address,
            postPhotoUrl = it.postImageUrl,
            creatorId = it.creatorId,
        )
    }.toImmutableList()

    private fun List<CarePost>.toMapMarkers() = map {
        MapsMarkerCluster(
            id = it.creatorId,
            itemPosition = it.location,
            itemTitle = it.name,
            itemSnippet = "$ ${it.price.orEmpty()}",
            itemZIndex = 0f,
            itemImage = it.photoUrl,
        )
    }.toImmutableList()
}
