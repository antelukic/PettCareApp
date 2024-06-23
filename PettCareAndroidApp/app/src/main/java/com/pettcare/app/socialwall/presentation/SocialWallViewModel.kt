package com.pettcare.app.socialwall.presentation

import com.pettcare.app.core.BaseResponse
import com.pettcare.app.core.BaseViewModel
import com.pettcare.app.navigation.Router
import com.pettcare.app.socialwall.domain.usecase.GetSocialWallPost
import com.pettcare.app.socialwall.domain.usecase.LikeSocialPost
import com.pettcare.app.socialwall.domain.usecase.PostSocialPostComment

class SocialWallViewModel(
    private val getSocialWallPost: GetSocialWallPost,
    private val likeSocialPost: LikeSocialPost,
    private val postComment: PostSocialPostComment,
    router: Router,
) : BaseViewModel<SocialWallUIState>(router, SocialWallUIState()) {

    init {
        launchInIO {
            getSocialWallPost.results().collect { response ->
                when (response) {
                    is BaseResponse.Success -> {
                        updateUiState { currentUiState ->
                            currentUiState.copy(
                                posts = response.data.toPresentableSocialPost(),
                            )
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

    fun showProfile(profileId: String) {
        publishNavigationAction {
            it.profile(profileId)
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
}
