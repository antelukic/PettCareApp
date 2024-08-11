package com.pettcare.app.socialwall.presentation

import com.pettcare.app.core.BaseResponse
import com.pettcare.app.core.BaseViewModel
import com.pettcare.app.navigation.Router
import com.pettcare.app.socialwall.domain.model.SocialWallPost
import com.pettcare.app.socialwall.domain.usecase.GetSocialWallPost
import com.pettcare.app.socialwall.domain.usecase.LikeSocialPost
import com.pettcare.app.socialwall.domain.usecase.PostSocialPostComment
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

class SocialWallViewModel(
    private val getSocialWallPost: GetSocialWallPost,
    private val likeSocialPost: LikeSocialPost,
    private val postComment: PostSocialPostComment,
    router: Router,
) : BaseViewModel<SocialWallUIState>(router, SocialWallUIState()) {

    private var shouldUpdatePage = true
    private var page = 0
    private var postIdForCommentsShown: String? = null

    init {
        launchInIO {
            getSocialWallPost.results().collect(::handleResult)
        }

        launchInIO {
            getSocialWallPost.publishPage(page)
        }

        launchInIO {
            likeSocialPost.results.collect {
                if (it is BaseResponse.Success) {
                    getSocialWallPost.publishPage(page)
                }
            }
        }

        launchInIO {
            postComment.results.collect {
                if (it is BaseResponse.Success) {
                    getSocialWallPost.publishPage(page)
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
        postIdForCommentsShown = postId
        updateUiState { state ->
            state.copy(comments = state.posts.firstOrNull { it.id == postId }?.commentsToShow)
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
            postComment.invoke(postIdForCommentsShown.orEmpty(), uiState.value.comment)
            dismissComments()
        }
    }

    fun nextPage() {
        launchInIO {
            if (shouldUpdatePage.not()) return@launchInIO
            page++
            getSocialWallPost.publishPage(page)
        }
    }

    private fun handleResult(response: BaseResponse<List<SocialWallPost>>) {
        when (response) {
            is BaseResponse.Success -> {
                shouldUpdatePage = response.data.isNotEmpty()
                updateUiState { currentUiState ->
                    currentUiState.copy(
                        posts = updateState(currentUiState.posts, response.data.toPresentableSocialPost()),
                    )
                }
            }

            else -> {}
        }
    }

    private fun updateState(
        oldList: List<PresentableSocialPost>,
        newList: List<PresentableSocialPost>,
    ): ImmutableList<PresentableSocialPost> {
        val listToReturn: MutableList<PresentableSocialPost> = mutableListOf()
        // Update old posts if need to
        oldList.forEach { oldPost ->
            val newPost = newList.find { it.id == oldPost.id }
            if (newPost == null) {
                listToReturn.add(oldPost)
            } else {
                listToReturn.add(newPost)
            }
        }
        // Add remaining posts
        newList.forEach { post ->
            if (listToReturn.find { it.id == post.id } == null) {
                listToReturn.add(post)
            }
        }
        return listToReturn.toImmutableList()
    }
}
