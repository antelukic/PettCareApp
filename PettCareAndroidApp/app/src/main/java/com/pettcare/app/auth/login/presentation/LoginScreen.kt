package com.pettcare.app.auth.login.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pettcare.app.R.dimen
import com.pettcare.app.uicomponents.PettCareInputField
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
                .padding(dimensionResource(id = dimen.spacing_4))

            Title(
                title = uiState.title,
                modifier = componentModifier,
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth(),
            ) {
                EmailInput(
                    email = uiState.email,
                    onUpdateEmail = onUpdateEmail,
                    modifier = componentModifier,
                    emailPlaceholder = uiState.emailPlaceholder,
                )

                Spacer(modifier = Modifier.height(dimensionResource(id = dimen.spacing_5)))

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

@Composable
private fun EmailInput(
    emailPlaceholder: String,
    email: String,
    onUpdateEmail: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    PettCareInputField(
        value = email,
        placeHolderText = emailPlaceholder,
        labelText = emailPlaceholder,
        onValueChange = onUpdateEmail,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        modifier = modifier,
    )
}

@Composable
private fun Title(
    title: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.headlineMedium,
    color: Color = MaterialTheme.colorScheme.primary,
    textAlign: TextAlign = TextAlign.Center,
) {
    Text(
        text = title,
        style = style,
        color = color,
        textAlign = textAlign,
        modifier = modifier,
    )
}

@Composable
private fun PasswordInput(
    passwordAccessibility: String,
    passwordPlaceholder: String,
    password: String,
    onUpdatePassword: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var passwordShowing by remember {
        mutableStateOf(false)
    }

    PettCareInputField(
        value = password,
        placeHolderText = passwordPlaceholder,
        labelText = passwordPlaceholder,
        onValueChange = onUpdatePassword,
        visualTransformation = if (passwordShowing) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { passwordShowing = !passwordShowing }) {
                Icon(
                    imageVector = if (passwordShowing) Filled.VisibilityOff else Filled.Visibility,
                    contentDescription = passwordAccessibility,
                )
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        modifier = modifier,
    )
}
