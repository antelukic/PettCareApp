package com.pettcare.app.uicomponents

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import com.pettcare.app.R

@Composable
fun Avatar(
    profilePhoto: String?,
    name: String,
    modifier: Modifier = Modifier,
    photoSize: Dp = dimensionResource(id = R.dimen.avatar_size),
) {
    Row(modifier = modifier) {
        UserProfilePhoto(photoUrl = profilePhoto, size = photoSize)

        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_2)))

        Text(
            text = name,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.align(Alignment.CenterVertically),
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}
