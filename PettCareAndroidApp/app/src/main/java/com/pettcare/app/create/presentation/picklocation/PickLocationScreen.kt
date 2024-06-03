package com.pettcare.app.create.presentation.picklocation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pettcare.app.R
import com.pettcare.app.uicomponents.PettCareInputField
import com.pettcare.app.uicomponents.SpinningProgressBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun PickLocationScreen(
    onLocationPicked: (PresentableLocation?) -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel = koinViewModel<PickLocationViewModel>()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    PickLocationScreen(
        uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
        onLocationPicked = { locationId ->
            val location = uiState.locations.firstOrNull { it.id == locationId }
            onLocationPicked(location)
        },
        onTextChanged = viewModel::updateQuery,
        modifier = modifier,
    )
}

@Composable
fun PickLocationScreen(
    uiState: PickLocationUiState,
    onLocationPicked: (String) -> Unit,
    onTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box {
        AnimatedVisibility(
            visible = uiState.isLoading,
            modifier = Modifier.fillMaxSize(),
        ) {
            SpinningProgressBar()
        }
        Column(modifier = modifier) {
            PettCareInputField(
                value = uiState.text,
                placeHolderText = stringResource(id = R.string.location_example),
                labelText = stringResource(id = R.string.what_is_the_location),
                onValueChange = onTextChanged,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.spacing_4)),
            )

            LazyColumn {
                itemsIndexed(uiState.locations) { index, location ->
                    LocationResult(
                        name = location.name,
                        address = location.address,
                        modifier = Modifier
                            .padding(dimensionResource(id = R.dimen.spacing_4))
                            .clickable { onLocationPicked(location.id) },
                    )

                    if (index != uiState.locations.lastIndex) {
                        HorizontalDivider(
                            thickness = dimensionResource(id = R.dimen.divider_thickness),
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(dimensionResource(id = R.dimen.spacing_4)),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LocationResult(
    name: String,
    address: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text(
            text = name,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_2)))

        Text(
            text = address,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}
