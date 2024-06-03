package com.pettcare.app.create.presentation.choosewhattocreate

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.pettcare.app.R
import com.pettcare.app.auth.sharedcomponents.Title
import com.pettcare.app.create.presentation.PostType
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChooseWhatToCreateScreen(
    modifier: Modifier = Modifier,
) {
    val viewModel = koinViewModel<ChooseWhatToCreateViewModel>()
    ChooseWhatToCreateScreen(
        onPicked = viewModel::onPicked,
        modifier = modifier,
    )
}

@Composable
private fun ChooseWhatToCreateScreen(
    onPicked: (PostType) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Title(
            title = stringResource(id = R.string.choose_what_to_create_title),
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.spacing_4)),
        )

        CreatePostInput(
            title = stringResource(id = R.string.create_social_post_label_title),
            subtitle = stringResource(id = R.string.create_social_post_label_subtitle),
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.spacing_4))
                .clickable { onPicked(PostType.SOCIAL) },
        )

        HorizontalDivider(
            thickness = dimensionResource(id = R.dimen.divider_thickness),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.spacing_4)),
        )

        CreatePostInput(
            title = stringResource(id = R.string.create_care_post_label_title),
            subtitle = stringResource(id = R.string.create_care_post_label_subtitle),
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.spacing_4))
                .clickable { onPicked(PostType.CARE) },
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_2)))
    }
}

@Composable
private fun CreatePostInput(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Icon(
            imageVector = Icons.Outlined.Home,
            contentDescription = null,
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.icon_size_big))
                .border(
                    dimensionResource(id = R.dimen.care_post_border_stroke_width),
                    MaterialTheme.colorScheme.onBackground,
                    CircleShape,
                )
                .padding(dimensionResource(id = R.dimen.spacing_1)),
        )

        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_2)))
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_1)))

            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}
