package com.pettcare.app.socialwall.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.pettcare.app.R
import com.pettcare.app.auth.sharedcomponents.Title
import com.pettcare.app.socialwall.presentation.comments.CommentsBottomSheet
import com.pettcare.app.uicomponents.Avatar
import org.koin.androidx.compose.koinViewModel

@Composable
fun SocialWallScreen(modifier: Modifier = Modifier) {
    val viewModel = koinViewModel<SocialWallViewModel>()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    SocialWallScreen(
        uiState = uiState,
        onProfileClick = viewModel::showProfile,
        onLikeClick = viewModel::likePost,
        onCommentsClick = viewModel::showComments,
        onDismiss = viewModel::dismissComments,
        onPostComment = viewModel::postComment,
        onUpdateComment = viewModel::updateComment,
        modifier = modifier,
    )
}

@Composable
fun SocialWallScreen(
    uiState: SocialWallUIState,
    onDismiss: () -> Unit,
    onProfileClick: (String) -> Unit,
    onLikeClick: (String) -> Unit,
    onCommentsClick: (String) -> Unit,
    onUpdateComment: (String) -> Unit,
    onPostComment: () -> Unit,
    modifier: Modifier = Modifier,
    title: String = stringResource(id = R.string.social_wall_title),
) {
    if (uiState.comments != null) {
        CommentsBottomSheet(
            comments = uiState.comments,
            comment = uiState.comment,
            onDismiss = onDismiss,
            onPostComment = onPostComment,
            onCommentUpdated = onUpdateComment,
            onProfileClick = onProfileClick,
        )
    }
    LazyColumn(modifier = modifier) {
        item {
            Title(
                title = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.spacing_4)),
            )
        }

        items(uiState.posts) { post ->
            SocialWallPost(
                post = post,
                onProfileClick = { onProfileClick(post.id) },
                onLikeClick = { onLikeClick(post.id) },
                onCommentsClick = { onCommentsClick(post.id) },
                modifier = Modifier.padding(dimensionResource(id = R.dimen.spacing_4)),
            )
        }
    }
}

@Composable
private fun SocialWallPost(
    post: PresentableSocialPost,
    onProfileClick: () -> Unit,
    onLikeClick: () -> Unit,
    onCommentsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
    ) {
        Avatar(
            profilePhoto = post.avatarUrl,
            name = post.creatorName,
            photoSize = dimensionResource(id = R.dimen.avatar_size),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onProfileClick() },
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_2)))

        PostContent(
            text = post.text,
            photoUrl = post.photoUrl,
            numOfLikes = post.numOfLikes,
            numOfComments = post.numOfComments,
            modifier = Modifier.fillMaxWidth(),
        )

        PostActionButtons(
            onLikeClick = onLikeClick,
            onCommentsClick = onCommentsClick,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
fun PostContent(
    text: String?,
    photoUrl: String?,
    numOfLikes: String,
    numOfComments: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        text?.let {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_2)))

        photoUrl?.let {
            AsyncImage(
                model = photoUrl,
                contentDescription = "Post photo",
                contentScale = ContentScale.Crop,
            )
        }

        Row {
            Text(
                text = stringResource(id = R.string.social_wall_num_of_likes, numOfLikes),
                style = MaterialTheme.typography.bodySmall,
            )

            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_2)))

            Text(
                text = stringResource(id = R.string.social_wall_num_of_comments, numOfComments),
                style = MaterialTheme.typography.bodySmall,
            )
        }

        HorizontalDivider(
            thickness = dimensionResource(id = R.dimen.divider_thickness),
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@Composable
private fun PostActionButtons(
    onLikeClick: () -> Unit,
    onCommentsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
    ) {
        Text(
            text = stringResource(id = R.string.social_wall_like_button_text),
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
                .padding(dimensionResource(id = R.dimen.spacing_2))
                .clickable(onClick = onLikeClick),
        )

        Text(
            text = stringResource(id = R.string.social_wall_comment_button_text),
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
                .padding(dimensionResource(id = R.dimen.spacing_2))
                .clickable(onClick = onCommentsClick),
        )
    }
}
