package com.pettcare.app.home.presentation

import com.pettcare.app.core.BaseResponse
import com.pettcare.app.core.BaseViewModel
import com.pettcare.app.home.domain.GetHomeData
import com.pettcare.app.home.domain.model.CarePostProfile
import com.pettcare.app.home.presentation.cluster.MapsMarkerCluster
import com.pettcare.app.navigation.Router
import kotlinx.collections.immutable.toImmutableList

class HomeViewModel(
    private val getHomeData: GetHomeData,
    router: Router,
    initialViewState: HomeUiState = HomeUiState(),
) : BaseViewModel<HomeUiState>(router, initialViewState) {

    fun onProfileClicked(id: String) {
        publishNavigationAction {
            it.profile(id)
        }
    }

    init {
        launchInIO {
            getHomeData.results().collect { response ->
                when (response) {
                    is BaseResponse.Loading -> {}
                    is BaseResponse.Error -> {}
                    is BaseResponse.Success -> updateUiState {
                        it.copy(
                            profiles = response.data.profiles.toPresentableProfiles(),
                            markers = response.data.profiles.toMapMarkers(),
                        )
                    }
                }
            }
        }
    }

    private fun List<CarePostProfile>.toPresentableProfiles() = map {
        PresentableProfiles(
            id = it.id,
            name = it.name,
            photoUrl = it.photoUrl,
            description = it.description,
            price = it.price,
            location = it.location,
            address = it.address,
        )
    }.toImmutableList()

    private fun List<CarePostProfile>.toMapMarkers() = map {
        MapsMarkerCluster(
            id = it.id,
            itemPosition = it.location,
            itemTitle = it.name,
            itemSnippet = it.price.orEmpty(),
            itemZIndex = 0f,
            itemImage = it.photoUrl,
        )
    }.toImmutableList()
}
