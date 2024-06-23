package com.pettcare.app.create.presentation.picklocation

import com.pettcare.app.core.BaseResponse
import com.pettcare.app.core.BaseViewModel
import com.pettcare.app.create.domain.model.Location
import com.pettcare.app.create.domain.usecase.PickLocationFromQuery
import com.pettcare.app.navigation.Router
import kotlinx.collections.immutable.toImmutableList

class PickLocationViewModel(
    private val getLocationFromQuery: PickLocationFromQuery,
    router: Router,
) : BaseViewModel<PickLocationUiState>(router, PickLocationUiState()) {

    init {
        launchInIO {
            getLocationFromQuery.results().collect { locations ->
                updateUiState { state ->
                    when (locations) {
                        is BaseResponse.Loading -> {
                            state.copy(isLoading = true)
                        }

                        is BaseResponse.Success -> {
                            state.copy(
                                locations = locations.data.toPresentableLocations(),
                                isLoading = false,
                            )
                        }

                        else -> {
                            state.copy(isLoading = false)
                        }
                    }
                }
            }
        }
    }

    fun updateQuery(text: String) = updateUiState { state ->
        launchInIO {
            getLocationFromQuery(text)
        }
        state.copy(text = text)
    }

    private fun List<Location>.toPresentableLocations() = map { location ->
        PresentableLocation(
            name = location.name,
            latLng = location.latLng,
            address = location.address,
        )
    }.toImmutableList()
}
