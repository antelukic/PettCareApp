package com.pettcare.app.profile.presentation

import com.pettcare.app.core.BaseResponse
import com.pettcare.app.core.BaseViewModel
import com.pettcare.app.navigation.Router
import com.pettcare.app.profile.domain.model.ProfileData
import com.pettcare.app.profile.domain.usecase.GetProfileData
import com.pettcare.app.socialwall.domain.usecase.LikeSocialPost
import com.pettcare.app.socialwall.domain.usecase.PostSocialPostComment
import com.pettcare.app.socialwall.presentation.PresentableSocialPost
import com.pettcare.app.socialwall.presentation.toPresentableSocialPost
import kotlinx.collections.immutable.toImmutableList

internal class ProfileViewModel(
    private val getProfileData: GetProfileData,
    private val likeSocialPost: LikeSocialPost,
    private val postComment: PostSocialPostComment,
    private val id: String,
    router: Router,
) : BaseViewModel<ProfileUiState>(router, ProfileUiState()) {

    private var shouldLoadNextPage = true
    private var page = 0

    init {
        launchInIO {
            getProfileData.results.collect(::handleResponse)
        }

        launchInIO {
            getProfileData(page, id)
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

    fun nextPage() {
        launchInIO {
            if (shouldLoadNextPage.not()) return@launchInIO
            page++
            getProfileData(page, id)
        }
    }

    private fun handleResponse(response: BaseResponse<ProfileData>) {
        when (response) {
            is BaseResponse.Success -> {
                updateUiState { state ->
                    response.data.toUiState(state.posts)
                }
            }

            else -> {}
        }
    }

    private fun ProfileData.toUiState(oldPosts: List<PresentableSocialPost>) = ProfileUiState(
        name = name,
        gender = gender,
        photoUrl = photoUrl,
        dateOfBirth = dateOfBirth,
        email = email,
        posts = (oldPosts + posts.toPresentableSocialPost()).toImmutableList(),
    )
}
