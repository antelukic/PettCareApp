package com.pettcare.app.socialwall.presentation.comments

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.pettcare.app.R
import com.pettcare.app.uicomponents.Avatar
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsBottomSheet(
    comments: ImmutableList<PresentableSocialPostComment>,
    comment: String,
    onPostComment: () -> Unit,
    onCommentUpdated: (String) -> Unit,
    onDismiss: () -> Unit,
    onProfileClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(),
        modifier = modifier,
    ) {
        LazyColumn(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.spacing_4)),
        ) {
            itemsIndexed(comments) { index, comment ->
                Comment(
                    avatarUrl = comment.avatarUrl,
                    name = comment.name,
                    text = comment.text,
                    onProfileClick = { onProfileClick(comment.id) },
                )

                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_2)))

                if (index != comments.lastIndex) {
                    HorizontalDivider(
                        thickness = dimensionResource(id = R.dimen.divider_thickness),
                        color = MaterialTheme.colorScheme.onBackground,
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_2)))
                }
            }

            item {
                Row {
                    OutlinedTextField(
                        value = comment,
                        onValueChange = onCommentUpdated,
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.text_field_corners_radius)),
                        modifier = Modifier.weight(1f),
                    )

                    Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_4)))

                    OutlinedButton(onClick = onPostComment) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = "Post comment",
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun Comment(
    avatarUrl: String?,
    name: String,
    text: String,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Avatar(
            profilePhoto = avatarUrl,
            name = name,
            photoSize = dimensionResource(id = R.dimen.avatar_size),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onProfileClick() },
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_2)))

        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
