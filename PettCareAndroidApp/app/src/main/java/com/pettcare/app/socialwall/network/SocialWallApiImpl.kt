package com.pettcare.app.socialwall.network

import com.pettcare.app.socialwall.network.model.ApiSocialWallPost
import com.pettcare.app.socialwall.network.model.ApiSocialWallPostComments

private const val LIST_SIZE = 20
private const val LIST_SIZE_COMMENTS = 20

internal class SocialWallApiImpl : SocialWallApi {

    override suspend fun results(page: Int): List<ApiSocialWallPost> = List(LIST_SIZE) {
        ApiSocialWallPost(
            id = "some id $it",
            creatorName = "Jhon Doe $it",
            avatarUrl = null,
            numOfComments = "4",
            numOfLikes = "1203",
            text = "Lorem ipsum something something",
            photoUrl = null,
            comments = List(LIST_SIZE_COMMENTS) {
                ApiSocialWallPostComments(
                    id = it.toString(),
                    creatorName = "Someone Important",
                    avatarUrl = null,
                    text = "This is a great app! Good job Ante.",
                )
            },
        )
    }

    override suspend fun likePost(postId: String) {
        // implement once backend is ready
    }

    override suspend fun postComment(comment: String) {
        // implement once backend is ready
    }
}
