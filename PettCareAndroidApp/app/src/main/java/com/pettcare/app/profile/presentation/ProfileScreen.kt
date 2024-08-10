package com.pettcare.app.profile.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pettcare.app.R
import com.pettcare.app.socialwall.presentation.SocialWallScreen
import com.pettcare.app.socialwall.presentation.SocialWallUIState
import com.pettcare.app.uicomponents.UserProfilePhoto
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ProfileScreen(
    id: String,
    modifier: Modifier = Modifier,
) {
    val viewModel = koinViewModel<ProfileViewModel>(parameters = { parametersOf(id) })
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    ProfileScreen(
        uiState = uiState,
        onPostComment = viewModel::postComment,
        onLikePost = viewModel::likePost,
        onShowComments = viewModel::showComments,
        onDismissComments = viewModel::dismissComments,
        onCommentTextChanged = viewModel::updateComment,
        loadMore = viewModel::nextPage,
        modifier = modifier.padding(dimensionResource(id = R.dimen.spacing_4)),
    )
}

@Composable
private fun ProfileScreen(
    uiState: ProfileUiState,
    onPostComment: () -> Unit,
    onLikePost: (String) -> Unit,
    onShowComments: (String) -> Unit,
    onDismissComments: () -> Unit,
    onCommentTextChanged: (String) -> Unit,
    loadMore: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        ProfileAvatar(
            name = uiState.name,
            photoUrl = uiState.photoUrl,
            modifier = Modifier.fillMaxWidth(),
        )

        HorizontalDivider(
            thickness = dimensionResource(id = R.dimen.divider_thickness),
            color = MaterialTheme.colorScheme.onBackground,
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_2)))

        ProfileInformation(email = uiState.email, gender = uiState.gender, dateOfBirth = uiState.dateOfBirth)

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_2)))

        HorizontalDivider(
            thickness = dimensionResource(id = R.dimen.divider_thickness),
            color = MaterialTheme.colorScheme.onBackground,
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_2)))

        SocialWallScreen(
            uiState = SocialWallUIState(uiState.posts, uiState.comments, uiState.comment),
            onDismiss = onDismissComments,
            onProfileClick = {},
            onLikeClick = onLikePost,
            onCommentsClick = onShowComments,
            onUpdateComment = onCommentTextChanged,
            onPostComment = onPostComment,
            loadMore = loadMore,
            title = stringResource(id = R.string.social_wall_title_profile),
        )
    }
}

@Composable
private fun ProfileAvatar(
    name: String,
    photoUrl: String?,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        UserProfilePhoto(
            photoUrl = photoUrl,
            size = dimensionResource(id = R.dimen.avatar_size_large),
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_2)))

        Text(
            text = name,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ProfileInformation(
    email: String,
    gender: String,
    dateOfBirth: String,
    modifier: Modifier = Modifier,
) {
    val itemModifier = Modifier
        .padding(dimensionResource(id = R.dimen.spacing_1))
        .border(
            width = dimensionResource(id = R.dimen.care_post_border_stroke_width),
            color = MaterialTheme.colorScheme.secondary,
            shape = CircleShape,
        )
        .padding(dimensionResource(id = R.dimen.spacing_2))
    FlowRow(modifier = modifier) {
        Text(
            text = email,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary,
            modifier = itemModifier,
        )
        Text(
            text = gender,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary,
            modifier = itemModifier,
        )
        Text(
            text = dateOfBirth,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary,
            modifier = itemModifier,
        )
    }
}
