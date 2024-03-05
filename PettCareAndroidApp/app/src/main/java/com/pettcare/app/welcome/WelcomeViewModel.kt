package com.pettcare.app.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pettcare.app.welcome.translations.WelcomeResources
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class WelcomeViewModel(
    private val welcomeResources: WelcomeResources,
) : ViewModel() {

    private val _uiState = MutableStateFlow(WelcomeScreenUIState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.emit(getUiState())
        }
    }

    private fun getUiState() = WelcomeScreenUIState(
        welcomeMessage = welcomeResources.welcomeMessage(),
        signInText = welcomeResources.signInMessage(),
        logInText = welcomeResources.logInMessage(),
    )

    fun navigateToLogin() = Unit

    fun navigateToSignIn() = Unit
}
