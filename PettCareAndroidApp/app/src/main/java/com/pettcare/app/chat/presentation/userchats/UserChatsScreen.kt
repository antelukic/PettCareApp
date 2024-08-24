package com.pettcare.app.chat.presentation.userchats

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pettcare.app.R
import com.pettcare.app.uicomponents.Avatar
import com.pettcare.app.uicomponents.PettCareInputField
import org.koin.androidx.compose.koinViewModel

@Composable
fun UserChatsScreen(modifier: Modifier = Modifier) {
    val viewModel = koinViewModel<UserChatsViewModel>()
    UserChatsScreen(
        uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
        onQueryUpdate = viewModel::updateQuery,
        onProfileClick = viewModel::launchChat,
        modifier = modifier,
    )
}

@Composable
private fun UserChatsScreen(
    uiState: UserChatsUiState,
    onQueryUpdate: (String) -> Unit,
    onProfileClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        item {
            PettCareInputField(
                value = uiState.query,
                placeHolderText = stringResource(id = R.string.search_users_hint),
                onValueChange = onQueryUpdate,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.spacing_4)),
            )
        }

        items(uiState.users) { user ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.spacing_4))
                    .background(MaterialTheme.colorScheme.background)
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.primary,
                        RoundedCornerShape(dimensionResource(id = R.dimen.text_field_corners_radius)),
                    )
                    .clickable { onProfileClick(user.id) },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Avatar(
                    profilePhoto = user.photoUrl,
                    name = user.name,
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.spacing_5)),
                )
            }
        }
    }
}
