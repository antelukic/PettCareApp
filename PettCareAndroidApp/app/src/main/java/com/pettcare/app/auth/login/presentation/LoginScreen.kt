package com.pettcare.app.auth.login.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pettcare.app.R
import com.pettcare.app.R.dimen
import com.pettcare.app.auth.sharedcomponents.EmailInput
import com.pettcare.app.auth.sharedcomponents.PasswordInput
import com.pettcare.app.auth.sharedcomponents.Title
import com.pettcare.app.uicomponents.PettCareProceedButton
import org.koin.androidx.compose.koinViewModel

@Composable
fun LogInScreen(
    modifier: Modifier = Modifier,
) {
    val viewModel = koinViewModel<LoginViewModel>()
    LogInScreen(
        uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
        onUpdateEmail = viewModel::updateEmail,
        onUpdatePassword = viewModel::updatePassword,
        onSignIn = viewModel::signIn,
        modifier = modifier,
    )
}

@Suppress("LongMethod")
@Composable
private fun LogInScreen(
    uiState: LoginUiState,
    onUpdateEmail: (String) -> Unit,
    onUpdatePassword: (String) -> Unit,
    onSignIn: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = modifier,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
        ) {
            val componentModifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = dimen.spacing_4))

            Title(
                title = uiState.title,
                modifier = componentModifier,
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(id = R.string.log_in_email_label),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Start,
                    modifier = componentModifier,
                )

                EmailInput(
                    email = uiState.email,
                    onUpdateEmail = onUpdateEmail,
                    modifier = componentModifier,
                    emailPlaceholder = uiState.emailPlaceholder,
                )

                Spacer(modifier = Modifier.height(dimensionResource(id = dimen.spacing_5)))

                Text(
                    text = stringResource(id = R.string.log_in_password_label),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Start,
                    modifier = componentModifier,
                )

                PasswordInput(
                    password = uiState.password,
                    onUpdatePassword = onUpdatePassword,
                    modifier = componentModifier,
                    passwordPlaceholder = uiState.passwordPlaceholder,
                    passwordAccessibility = uiState.passwordAccessibility,
                )
            }

            PettCareProceedButton(
                text = uiState.btnContinueTxt,
                isEnabled = uiState.isLoginButtonEnabled,
                onClick = onSignIn,
            )
        }
    }

    LaunchedEffect(key1 = uiState.isError) {
        if (uiState.isError) {
            snackbarHostState.showSnackbar(uiState.errorMessage)
        }
    }
}
