package com.pettcare.app.create.presentation.createpost

import android.net.Uri
import com.pettcare.app.core.BaseViewModel
import com.pettcare.app.create.domain.model.CarePostParams
import com.pettcare.app.create.domain.model.SocialPostParams
import com.pettcare.app.create.domain.usecase.CreateCarePost
import com.pettcare.app.create.domain.usecase.CreateSocialPost
import com.pettcare.app.create.presentation.PostType
import com.pettcare.app.create.presentation.picklocation.PresentableLocation
import com.pettcare.app.navigation.Router

class CreatePostViewModel(
    private val postType: PostType,
    private val createCarePost: CreateCarePost,
    private val createSocialPost: CreateSocialPost,
    router: Router,
) : BaseViewModel<CreatePostUiState>(router, CreatePostUiState(postType = postType)) {

    init {
        launchInMain {
            uiState.collect {
                shouldEnablePostButton()
            }
        }
    }

    fun onTextUpdate(text: String) = updateUiState { state ->
        state.copy(text = text)
    }

    fun onPriceUpdate(price: String) = updateUiState { state ->
        state.copy(price = price)
    }

    fun toggleAddressBottomSheet(shouldExpand: Boolean) = updateUiState { state ->
        state.copy(showAddressBottomSheet = shouldExpand)
    }

    fun onPhotoPicked(value: Uri?) {
        updateUiState { state -> state.copy(photo = value) }
    }

    fun onLocationPicked(location: PresentableLocation?) = updateUiState { state ->
        if (location == null) return@updateUiState state
        state.copy(
            lon = location.latLng.longitude,
            lat = location.latLng.latitude,
            address = location.address,
        )
    }

    fun post() = launchInIO {
        val state = uiState.value
        if (postType == PostType.CARE) {
            if (state.lat == null || state.lon == null || state.address == null) return@launchInIO
            createCarePost(
                CarePostParams(
                    description = state.text,
                    lat = state.lat,
                    lon = state.lon,
                    address = state.address,
                    price = state.price,
                    photo = state.photo,
                ),
            )
        } else {
            createSocialPost(
                SocialPostParams(
                    state.text,
                    state.photo,
                ),
            )
        }
    }

    private fun shouldEnablePostButton() = updateUiState { state ->
        val shouldEnable = if (postType == PostType.SOCIAL) {
            state.text.isNotBlank()
        } else {
            state.text.isNotBlank() && state.address?.isNotBlank() == true && state.price.isNotBlank()
        }
        state.copy(isPostButtonEnabled = shouldEnable)
    }

    fun onNavigateBack() = publishNavigationAction(Router::goBack)
}
