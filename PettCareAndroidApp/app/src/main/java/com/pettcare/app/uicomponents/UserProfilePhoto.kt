package com.pettcare.app.uicomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import com.pettcare.app.R

@Composable
fun UserProfilePhoto(
    photoUrl: String?,
    modifier: Modifier = Modifier,
    size: Dp = dimensionResource(id = R.dimen.avatar_size),
) {
    if (photoUrl.isNullOrBlank().not()) {
        AsyncImage(
            model = photoUrl,
            contentDescription = "Profile image",
            contentScale = ContentScale.Crop,
            modifier = modifier
                .clip(CircleShape)
                .border(
                    width = dimensionResource(
                        id = R.dimen.care_post_border_stroke_width,
                    ),
                    color = MaterialTheme.colorScheme.onBackground,
                    shape = CircleShape,
                )
                .size(size),
        )
    } else {
        Image(
            imageVector = Icons.Default.Person,
            contentDescription = "Profile image",
            modifier = modifier
                .clip(CircleShape)
                .border(
                    width = dimensionResource(
                        id = R.dimen.care_post_border_stroke_width,
                    ),
                    color = MaterialTheme.colorScheme.onBackground,
                    shape = CircleShape,
                )
                .size(size),
        )
    }
}
