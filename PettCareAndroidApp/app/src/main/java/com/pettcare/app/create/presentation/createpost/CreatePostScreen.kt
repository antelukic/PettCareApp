package com.pettcare.app.create.presentation.createpost

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronLeft
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pettcare.app.R
import com.pettcare.app.create.presentation.PostType
import com.pettcare.app.create.presentation.picklocation.PickLocationScreen
import com.pettcare.app.create.presentation.picklocation.PresentableLocation
import com.pettcare.app.uicomponents.PettCareInputField
import com.pettcare.app.uicomponents.PettCareProceedButton
import com.pettcare.app.uicomponents.PhotoPicker
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

private const val TEXT_MIN_LINES = 5

@Composable
fun CreatePostScreen(
    postType: PostType,
    modifier: Modifier = Modifier,
) {
    val viewModel = koinViewModel<CreatePostViewModel>(parameters = { parametersOf(postType) })
    CreatePostScreen(
        uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
        onTextUpdate = viewModel::onTextUpdate,
        toggleAddressPickerVisibility = viewModel::toggleAddressBottomSheet,
        onPhotoPicked = viewModel::onPhotoPicked,
        onNavigateBack = viewModel::onNavigateBack,
        onPriceUpdate = viewModel::onPriceUpdate,
        onLocationPicked = viewModel::onLocationPicked,
        onPostClicked = viewModel::post,
        modifier = modifier,
    )
}

@Composable
private fun CreatePostScreen(
    uiState: CreatePostUiState,
    onTextUpdate: (String) -> Unit,
    toggleAddressPickerVisibility: (Boolean) -> Unit,
    onPhotoPicked: (Uri?) -> Unit,
    onPriceUpdate: (String) -> Unit,
    onNavigateBack: () -> Unit,
    onPostClicked: () -> Unit,
    onLocationPicked: (PresentableLocation?) -> Unit,
    modifier: Modifier = Modifier,
) {
    val imageLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia(),
    ) { uri ->
        onPhotoPicked(uri)
    }

    if (uiState.showAddressBottomSheet) {
        PickAddressBottomSheet(
            onLocationPicked = onLocationPicked,
            onDismiss = {
                toggleAddressPickerVisibility(false)
            },
        )
    }

    ScreenContent(
        imageLauncher = imageLauncher,
        uiState = uiState,
        onTextUpdate = onTextUpdate,
        toggleAddressPickerVisibility = toggleAddressPickerVisibility,
        onPriceUpdate = onPriceUpdate,
        onNavigateBack = onNavigateBack,
        onPostClicked = onPostClicked,
        modifier = modifier,
    )
}

@Suppress("LongMethod")
@Composable
private fun ScreenContent(
    imageLauncher: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>,
    uiState: CreatePostUiState,
    onTextUpdate: (String) -> Unit,
    toggleAddressPickerVisibility: (Boolean) -> Unit,
    onPriceUpdate: (String) -> Unit,
    onNavigateBack: () -> Unit,
    onPostClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacing_4)),
        ) {
            item {
                Header(
                    onNavigateBack = onNavigateBack,
                    imageLauncher = imageLauncher,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            item {
                AnimatedVisibility(visible = uiState.photo != null) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        PhotoPicker(
                            photoUri = uiState.photo,
                            onPhotoPicked = null,
                            modifier = Modifier
                                .size(dimensionResource(id = R.dimen.registration_photo_size))
                                .background(Color.Gray)
                                .align(Alignment.Center),
                            launcher = imageLauncher,
                        )
                    }
                }
            }
            item {
                PettCareInputField(
                    value = uiState.text,
                    placeHolderText = stringResource(id = R.string.post_text_placeholder),
                    labelText = stringResource(id = R.string.post_text_placeholder),
                    onValueChange = onTextUpdate,
                    maxLines = Int.MAX_VALUE,
                    minLines = TEXT_MIN_LINES,
                    keyboardOptions = KeyboardOptions.Default,
                    modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.spacing_4)),
                )
            }
            item {
                AnimatedVisibility(visible = uiState.postType == PostType.CARE) {
                    CarePostAdditionalInputFields(
                        address = uiState.address,
                        price = uiState.price,
                        toggleAddressPickerVisibility = toggleAddressPickerVisibility,
                        onPriceUpdate = onPriceUpdate,
                    )
                }
            }
        }
        PettCareProceedButton(
            text = stringResource(id = R.string.post_button_text),
            isEnabled = uiState.isPostButtonEnabled,
            onClick = onPostClicked,
            textStyle = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.spacing_5)),
        )
    }
}

@Composable
private fun Header(
    onNavigateBack: () -> Unit,
    imageLauncher: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Icon(
            imageVector = Icons.Outlined.ChevronLeft,
            contentDescription = stringResource(id = R.string.choose_image_content_description),
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(dimensionResource(id = R.dimen.spacing_4))
                .clickable {
                    onNavigateBack()
                },
        )

        Icon(
            imageVector = Icons.Outlined.Image,
            contentDescription = stringResource(id = R.string.choose_image_content_description),
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(dimensionResource(id = R.dimen.spacing_4))
                .clickable {
                    imageLauncher.launch(
                        PickVisualMediaRequest(
                            mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly,
                        ),
                    )
                },
        )
    }
}

@Composable
private fun CarePostAdditionalInputFields(
    address: String?,
    price: String,
    toggleAddressPickerVisibility: (Boolean) -> Unit,
    onPriceUpdate: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        PettCareInputField(
            value = price,
            placeHolderText = stringResource(id = R.string.post_price_placeholder),
            labelText = stringResource(id = R.string.post_price_placeholder),
            onValueChange = onPriceUpdate,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.spacing_4)),
        )

        address?.let {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_4)))

            PettCareInputField(
                value = address,
                placeHolderText = stringResource(id = R.string.post_address_placeholder),
                labelText = stringResource(id = R.string.post_address_placeholder),
                onValueChange = {},
                onFocusChanged = { focusState ->
                    toggleAddressPickerVisibility(focusState.isFocused)
                },
                readOnly = true,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.spacing_4)),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PickAddressBottomSheet(
    onDismiss: () -> Unit,
    onLocationPicked: (PresentableLocation?) -> Unit,
    modifier: Modifier = Modifier,
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        modifier = modifier,
    ) {
        PickLocationScreen(
            onLocationPicked = {
                onLocationPicked(it)
                onDismiss()
            },
            modifier = Modifier.fillMaxSize(),
        )
    }
}
