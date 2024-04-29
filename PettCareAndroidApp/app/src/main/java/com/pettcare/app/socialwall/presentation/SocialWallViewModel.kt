package com.pettcare.app.socialwall.presentation

import com.pettcare.app.core.BaseResponse
import com.pettcare.app.core.BaseViewModel
import com.pettcare.app.navigation.Router
import com.pettcare.app.socialwall.domain.model.SocialPostComment
import com.pettcare.app.socialwall.domain.model.SocialWallPost
import com.pettcare.app.socialwall.domain.usecase.GetSocialWallPost
import com.pettcare.app.socialwall.domain.usecase.LikeSocialPost
import com.pettcare.app.socialwall.domain.usecase.PostSocialPostComment
import com.pettcare.app.socialwall.presentation.comments.PresentableSocialPostComment
import kotlinx.collections.immutable.toImmutableList

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

    private fun List<SocialWallPost>.toPresentableSocialPost() = map {
        PresentableSocialPost(
            id = it.id,
            creatorName = it.creatorName,
            avatarUrl = it.avatarUrl,
            photoUrl = it.photoUrl,
            numOfLikes = it.numOfLikes,
            numOfComments = it.numOfComments,
            text = it.text,
            comments = it.comments.map { comment -> comment.toPresentableSocialPostComment() }.toImmutableList(),
        )
    }.toImmutableList()

    private fun SocialPostComment.toPresentableSocialPostComment() = PresentableSocialPostComment(
        id = id,
        name = name,
        avatarUrl = avatarUrl,
        text = text,
    )
}
