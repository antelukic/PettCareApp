package com.pettcare.app.home.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import com.pettcare.app.R
import com.pettcare.app.auth.sharedcomponents.Title
import kotlinx.collections.immutable.ImmutableList

@Composable
fun ListPage(
    profiles: ImmutableList<PresentableProfiles>,
    onProfileClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier) {
        item {
            Title(
                title = stringResource(id = R.string.home_title),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.spacing_4)),
            )
        }
        itemsIndexed(profiles) { index, profile ->
            if (index != 0) {
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_4)))
            }
            CarePostProfile(
                profilePicture = profile.photoUrl,
                name = profile.name,
                price = profile.price,
                description = profile.description,
                address = profile.address,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.spacing_4))
                    .clickable { onProfileClicked(profile.id) },
            )
            if (index == profiles.lastIndex) {
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_10)))
            }
        }
    }
}

@Composable
fun CarePostProfile(
    profilePicture: String?,
    name: String,
    price: String?,
    description: String,
    address: String?,
    modifier: Modifier = Modifier,
    cardPadding: Dp = dimensionResource(id = R.dimen.spacing_4),
) {
    Card(
        modifier = modifier,
        border = BorderStroke(
            width = dimensionResource(id = R.dimen.care_post_border_stroke_width),
            color = MaterialTheme.colorScheme.onBackground,
        ),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
    ) {
        Column(modifier = Modifier.padding(cardPadding)) {
            Row {
                UserProfile(
                    photoUrl = profilePicture,
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.care_post_avatar_size))
                        .clip(CircleShape)
                        .border(
                            width = dimensionResource(
                                id = R.dimen.care_post_border_stroke_width,
                            ),
                            color = MaterialTheme.colorScheme.onBackground,
                            shape = CircleShape,
                        )
                        .align(Alignment.CenterVertically),
                )

                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_2)))

                Column {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.titleSmall,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                    price.Price()
                    address.Address()
                }
            }

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
private fun String?.Address(
    modifier: Modifier = Modifier,
) {
    this?.let { address ->
        Column(modifier = modifier) {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_1)))

            Text(
                text = address,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
private fun String?.Price(
    modifier: Modifier = Modifier,
) {
    this?.let { price ->
        Column(modifier = modifier) {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_1)))

            Text(
                text = stringResource(id = R.string.price, price),
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
private fun UserProfile(
    photoUrl: String?,
    modifier: Modifier = Modifier,
) {
    if (photoUrl != null) {
        AsyncImage(
            model = photoUrl,
            contentDescription = "Profile image",
            modifier = modifier,
        )
    } else {
        Image(
            imageVector = Icons.Default.Person,
            contentDescription = "Profile image",
            modifier = modifier,
        )
    }
}
