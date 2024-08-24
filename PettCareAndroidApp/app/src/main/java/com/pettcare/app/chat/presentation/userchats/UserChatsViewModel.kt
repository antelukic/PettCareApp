package com.pettcare.app.chat.presentation.userchats

import com.pettcare.app.chat.domain.usecase.GetUserChats
import com.pettcare.app.core.BaseResponse
import com.pettcare.app.core.BaseViewModel
import com.pettcare.app.navigation.Router
import com.pettcare.app.profile.domain.model.ProfileData

class UserChatsViewModel(
    private val getUserChats: GetUserChats,
    router: Router,
) : BaseViewModel<UserChatsUiState>(router, UserChatsUiState()) {

    init {
        launchInIO {
            getUserChats("")
        }

        launchInIO {
            getUserChats.results.collect(::handleResponse)
        }
    }

    fun updateQuery(query: String) {
        updateUiState { state ->
            state.copy(query = query)
        }
        launchInIO {
            getUserChats(query)
        }
    }

    fun launchChat(userId: String) {
        publishNavigationAction { router ->
            router.messages(userId)
        }
    }

    private fun handleResponse(response: BaseResponse<List<ProfileData>>) {
        if (response is BaseResponse.Success) {
            updateUiState { state ->
                state.copy(
                    users = response.data.map { profileData ->
                        UserChatUiState(profileData.photoUrl.orEmpty(), profileData.name, profileData.id)
                    },
                )
            }
        }
    }
}
