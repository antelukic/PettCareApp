package com.pettcare.app.welcome.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pettcare.app.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun WelcomeScreen(modifier: Modifier = Modifier) {
    val viewModel = koinViewModel<WelcomeViewModel>()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    Box(modifier = modifier) {
        WelcomeMessage(
            welcomeMessage = uiState.welcomeMessage,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(horizontal = dimensionResource(id = R.dimen.spacing_4)),
        )

        ActionButtons(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(dimensionResource(id = R.dimen.spacing_4)),
            logInText = uiState.logInText,
            signInText = uiState.signInText,
            onSignInClick = viewModel::navigateToSignIn,
            onLogInClick = viewModel::navigateToLogin,
        )
    }
}

@Composable
private fun WelcomeMessage(
    welcomeMessage: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = welcomeMessage,
        modifier = modifier,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineLarge,
    )
}

@Composable
private fun ActionButtons(
    logInText: String,
    signInText: String,
    onSignInClick: () -> Unit,
    onLogInClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
    ) {
        val buttonModifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(id = R.dimen.spacing_4),
                vertical = dimensionResource(id = R.dimen.spacing_1),
            )
        Button(
            onClick = onLogInClick,
            modifier = buttonModifier,
        ) {
            Text(
                text = logInText,
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        Button(
            onClick = onSignInClick,
            modifier = buttonModifier,
        ) {
            Text(
                text = signInText,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_4)))
    }
}
