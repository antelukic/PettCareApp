package com.pettcare.app.welcome

import androidx.lifecycle.viewModelScope
import com.pettcare.app.core.BaseViewModel
import com.pettcare.app.navigation.Router
import com.pettcare.app.welcome.translations.WelcomeResources
import kotlinx.coroutines.launch

internal class WelcomeViewModel(
    private val welcomeResources: WelcomeResources,
    router: Router,
) : BaseViewModel<WelcomeScreenUIState>(router = router, WelcomeScreenUIState()) {

    init {
        viewModelScope.launch {
            updateUiState(getUiState())
        }
    }

    private fun getUiState() = WelcomeScreenUIState(
        welcomeMessage = welcomeResources.welcomeMessage(),
        signInText = welcomeResources.signInMessage(),
        logInText = welcomeResources.logInMessage(),
    )

    fun navigateToLogin() = publishNavigationAction {
        it.loginScreen()
    }

    fun navigateToSignIn() = publishNavigationAction {
        it.signInScreen()
    }
}
