package com.pettcare.app.profile.presentation

import com.pettcare.app.core.BaseResponse
import com.pettcare.app.core.BaseViewModel
import com.pettcare.app.navigation.Router
import com.pettcare.app.profile.domain.model.ProfileData
import com.pettcare.app.profile.domain.usecase.GetProfileData
import com.pettcare.app.socialwall.domain.usecase.LikeSocialPost
import com.pettcare.app.socialwall.domain.usecase.PostSocialPostComment
import com.pettcare.app.socialwall.presentation.toPresentableSocialPost

internal class ProfileViewModel(
    private val getProfileData: GetProfileData,
    private val likeSocialPost: LikeSocialPost,
    private val postComment: PostSocialPostComment,
    id: String,
    router: Router,
) : BaseViewModel<ProfileUiState>(router, ProfileUiState()) {

    init {
        launchInIO {
            getProfileData(id).collect { response ->
                when (response) {
                    is BaseResponse.Success -> {
                        updateUiState {
                            response.data.toUiState()
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    fun likePost(postId: String) {
        launchInIO {
            likeSocialPost.invoke(postId)
        }
    }

    fun showComments(postId: String) {
        updateUiState { state ->
            state.copy(comments = state.posts.firstOrNull { it.id == postId }?.comments)
        }
    }

    fun dismissComments() {
        updateUiState { state ->
            state.copy(comments = null)
        }
    }

    fun updateComment(value: String) {
        updateUiState { state ->
            state.copy(comment = value)
        }
    }

    fun postComment() {
        launchInIO {
            postComment(uiState.value.comment)
        }
    }

    private fun ProfileData.toUiState() = ProfileUiState(
        name = name,
        gender = gender,
        photoUrl = photoUrl,
        dateOfBirth = dateOfBirth,
        email = email,
        posts = posts.toPresentableSocialPost(),
    )
}
