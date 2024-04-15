package com.pettcare.app.auth.signin.presentation

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.pettcare.app.R
import com.pettcare.app.auth.sharedcomponents.EmailInput
import com.pettcare.app.auth.sharedcomponents.PasswordInput
import com.pettcare.app.auth.sharedcomponents.Title
import com.pettcare.app.uicomponents.PettCareInputField
import com.pettcare.app.uicomponents.PettCareProceedButton
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

private const val PAGES_COUNT = 4

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
) {
    val viewModel = koinViewModel<SignInViewModel>()
    SignInScreen(
        uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
        callbacks = RegistrationScreenCallbacks(
            onNameChanged = viewModel::onNameChanged,
            onSurnameChanged = viewModel::onSurnameChanged,
            onGenderSelected = { viewModel.onGenderSelected(it) },
            onPasswordChanged = viewModel::onPasswordChanged,
            onRepeatPasswordChanged = viewModel::onRepeatPasswordChanged,
            onEmailChanged = viewModel::onEmailChanged,
            onTriggerDatePickerVisibility = viewModel::onTriggerDatePickerVisibility,
            onSubmitClicked = viewModel::onSubmit,
            onPageUpdate = viewModel::onPageUpdate,
            onAgeSelected = viewModel::onAgeSelected,
            onTriggerGenderPickerVisibility = viewModel::onTriggerGenderVisibility,
            onPhotoPicked = viewModel::onPhotoPicked,
        ),
        modifier = modifier,
    )
}

@Suppress("LongMethod", "MagicNumber")
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SignInScreen(
    uiState: SignInUiState,
    callbacks: RegistrationScreenCallbacks,
    modifier: Modifier = Modifier,
) {
    val componentModifier = Modifier
        .fillMaxWidth()
        .padding(dimensionResource(id = R.dimen.spacing_4))

    val pagerState = rememberPagerState(pageCount = { PAGES_COUNT })
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = uiState.errorMessage) {
        if (uiState.errorMessage != null) {
            snackbarHostState.showSnackbar(uiState.errorMessage)
        }
    }

    DatePicker(
        datePickerConfirmButtonTxt = uiState.datePickerConfirmButtonTxt,
        isDatePickerVisible = uiState.isDatePickerVisible,
        onTriggerDatePickerVisibility = callbacks.onTriggerDatePickerVisibility,
        onAgeSelected = callbacks.onAgeSelected,
    )

    if (uiState.isGendersBottomSheetVisible) {
        GenderPicker(
            genders = uiState.gender.texts,
            onDismiss = { callbacks.onTriggerGenderPickerVisibility(false) },
            onGenderSelected = callbacks.onGenderSelected,
            modifier = Modifier.fillMaxWidth(),
        )
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = modifier,
    ) {
        Column(modifier = Modifier.padding(it)) {
            Title(
                title = uiState.title,
                modifier = componentModifier,
            )
            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false,
            ) { page ->
                when (page) {
                    0 -> NameInputPage(
                        uiState = uiState,
                        onNameChanged = callbacks.onNameChanged,
                        onSurnameChanged = callbacks.onSurnameChanged,
                    )

                    1 -> EmailPasswordPage(
                        uiState = uiState,
                        onEmailChanged = callbacks.onEmailChanged,
                        onPasswordChanged = callbacks.onPasswordChanged,
                        onRepeatPasswordChanged = callbacks.onRepeatPasswordChanged,
                    )

                    2 -> InfoPage(
                        uiState = uiState,
                        onTriggerDatePickerVisibility = callbacks.onTriggerDatePickerVisibility,
                        onTriggerGenderPickerVisibility = callbacks.onTriggerGenderPickerVisibility,
                    )

                    3 -> Box(modifier = Modifier.fillMaxWidth()) {
                        PhotoPicker(
                            photoUri = uiState.photo,
                            onPhotoPicked = callbacks.onPhotoPicked,
                            modifier = Modifier
                                .size(dimensionResource(id = R.dimen.registration_photo_size))
                                .background(Color.Gray)
                                .align(Alignment.Center),
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_4)))

            NavigationButtons(
                btnPreviousText = uiState.btnPreviousText,
                btnNextText = uiState.btnNextText,
                currentPage = pagerState.currentPage,
                onBtnPreviousClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage.dec())
                    }
                    callbacks.onPageUpdate(false)
                },
                onBtnNextClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage.inc())
                        val isLastPage = pagerState.currentPage == pagerState.pageCount.dec()
                        callbacks.onPageUpdate(isLastPage)
                        if (isLastPage) callbacks.onSubmitClicked()
                    }
                },
                isLoading = uiState.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.spacing_4)),
            )
        }
    }
}

@Composable
private fun PhotoPicker(
    photoUri: Uri?,
    onPhotoPicked: (Uri?) -> Unit,
    modifier: Modifier = Modifier,
) {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        onPhotoPicked(uri)
    }
    Box(
        modifier = modifier
            .then(
                Modifier.clickable {
                    launcher.launch(
                        PickVisualMediaRequest(
                            mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly,
                        ),
                    )
                },
            ),
    ) {
        if (photoUri != null) {
            val painter = rememberAsyncImagePainter(
                ImageRequest
                    .Builder(LocalContext.current)
                    .data(data = photoUri)
                    .build(),
            )

            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GenderPicker(
    genders: ImmutableList<Pair<String, String>>,
    onDismiss: () -> Unit,
    onGenderSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = rememberModalBottomSheetState(),
    ) {
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(dimensionResource(id = R.dimen.spacing_4)),
        ) {
            itemsIndexed(genders) { index, (id, value) ->
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .clickable { onGenderSelected(id) }
                        .padding(vertical = dimensionResource(id = R.dimen.spacing_4))
                        .fillMaxWidth(),
                )
                if (index < genders.lastIndex) {
                    HorizontalDivider()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePicker(
    datePickerConfirmButtonTxt: String,
    isDatePickerVisible: Boolean,
    onTriggerDatePickerVisibility: (Boolean) -> Unit,
    onAgeSelected: (Long?) -> Unit,
    modifier: Modifier = Modifier,
) {
    val datePickerState = rememberDatePickerState()

    AnimatedVisibility(
        visible = isDatePickerVisible,
        modifier = modifier,
    ) {
        DatePickerDialog(
            onDismissRequest = { onTriggerDatePickerVisibility(false) },
            confirmButton = {
                Button(onClick = { onAgeSelected(datePickerState.selectedDateMillis) }) {
                    Text(
                        text = datePickerConfirmButtonTxt,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            },
            content = {
                DatePicker(state = datePickerState)
            },
        )
    }
}

@Composable
private fun NavigationButtons(
    btnPreviousText: String,
    btnNextText: String,
    currentPage: Int,
    isLoading: Boolean,
    onBtnPreviousClick: () -> Unit,
    onBtnNextClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AnimatedVisibility(visible = currentPage != 0) {
            Button(
                onClick = onBtnPreviousClick,
            ) {
                Text(
                    text = btnPreviousText,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }

        PettCareProceedButton(
            onClick = onBtnNextClick,
            isLoading = isLoading,
            textStyle = MaterialTheme.typography.bodySmall,
            text = btnNextText,
            isEnabled = !isLoading,
        )
    }
}

@Composable
fun NameInputPage(
    uiState: SignInUiState,
    onNameChanged: (String) -> Unit,
    onSurnameChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val componentModifier = Modifier
        .fillMaxWidth()
        .padding(dimensionResource(id = R.dimen.spacing_4))
    Column(modifier = modifier) {
        PettCareInputField(
            value = uiState.name.text,
            placeHolderText = uiState.name.placeholder,
            onValueChange = onNameChanged,
            labelText = uiState.name.label,
            isError = uiState.name.isError,
            errorText = uiState.name.errorText,
            modifier = componentModifier,
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_4)))

        PettCareInputField(
            value = uiState.surname.text,
            placeHolderText = uiState.surname.placeholder,
            onValueChange = onSurnameChanged,
            labelText = uiState.surname.label,
            isError = uiState.surname.isError,
            errorText = uiState.surname.errorText,
            modifier = componentModifier,
        )
    }
}

@Composable
fun EmailPasswordPage(
    uiState: SignInUiState,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onRepeatPasswordChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val componentModifier = Modifier
        .fillMaxWidth()
        .padding(dimensionResource(id = R.dimen.spacing_4))
    Column(modifier = modifier) {
        EmailInput(
            emailPlaceholder = uiState.email.placeholder,
            email = uiState.email.text,
            isError = uiState.email.isError,
            errorText = uiState.email.errorText,
            emailLabel = uiState.email.label,
            onUpdateEmail = onEmailChanged,
            modifier = componentModifier,
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_4)))

        PasswordInput(
            password = uiState.password.text,
            passwordPlaceholder = uiState.password.placeholder,
            passwordLabel = uiState.password.label,
            passwordAccessibility = uiState.password.label,
            onUpdatePassword = onPasswordChanged,
            isError = uiState.surname.isError,
            errorText = uiState.surname.errorText,
            modifier = componentModifier,
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_4)))

        PasswordInput(
            password = uiState.repeatPassword.text,
            passwordPlaceholder = uiState.repeatPassword.placeholder,
            passwordLabel = uiState.repeatPassword.label,
            passwordAccessibility = uiState.repeatPassword.label,
            onUpdatePassword = onRepeatPasswordChanged,
            isError = uiState.repeatPassword.isError,
            errorText = uiState.repeatPassword.errorText,
            modifier = componentModifier,
        )
    }
}

@Composable
fun InfoPage(
    uiState: SignInUiState,
    onTriggerDatePickerVisibility: (Boolean) -> Unit,
    onTriggerGenderPickerVisibility: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val componentModifier = Modifier
        .fillMaxWidth()
        .padding(dimensionResource(id = R.dimen.spacing_4))
    Column(modifier = modifier) {
        PettCareInputField(
            value = uiState.age.text,
            placeHolderText = uiState.age.placeholder,
            labelText = uiState.age.label,
            isError = uiState.age.isError,
            errorText = uiState.age.errorText,
            onValueChange = { },
            modifier = componentModifier,
            onFocusChanged = {
                onTriggerDatePickerVisibility(it.hasFocus)
            },
            readOnly = true,
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_4)))

        PettCareInputField(
            value = uiState.gender.text,
            placeHolderText = uiState.gender.placeholder,
            labelText = uiState.gender.label,
            isError = uiState.gender.isError,
            errorText = uiState.gender.errorText,
            onValueChange = { },
            modifier = componentModifier,
            onFocusChanged = {
                onTriggerGenderPickerVisibility(it.hasFocus)
            },
            readOnly = true,
        )
    }
}

private data class RegistrationScreenCallbacks(
    inline val onNameChanged: (String) -> Unit,
    inline val onSurnameChanged: (String) -> Unit,
    inline val onGenderSelected: (String) -> Unit,
    inline val onPasswordChanged: (String) -> Unit,
    inline val onRepeatPasswordChanged: (String) -> Unit,
    inline val onEmailChanged: (String) -> Unit,
    inline val onTriggerDatePickerVisibility: (Boolean) -> Unit,
    inline val onTriggerGenderPickerVisibility: (Boolean) -> Unit,
    inline val onAgeSelected: (Long?) -> Unit,
    inline val onSubmitClicked: () -> Unit,
    inline val onPageUpdate: (Boolean) -> Unit,
    inline val onPhotoPicked: (Uri?) -> Unit,
)
