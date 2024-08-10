package com.pettcare.app.welcome.presentation

import com.pettcare.app.core.BaseViewModel
import com.pettcare.app.navigation.Router
import com.pettcare.app.welcome.domain.usecase.AuthenticateUser
import com.pettcare.app.welcome.presentation.translations.WelcomeResources

internal class WelcomeViewModel(
    authenticateUser: AuthenticateUser,
    welcomeResources: WelcomeResources,
    router: Router,
) : BaseViewModel<WelcomeScreenUIState>(router = router, WelcomeScreenUIState(welcomeResources)) {

    init {
        launchInIO {
            authenticateUser()
        }

        launchInMain {
            authenticateUser.results.collect(::handleAuthenticationResults)
        }
    }

    fun navigateToLogin() = publishNavigationAction {
        it.loginScreen()
    }

    fun navigateToSignIn() = publishNavigationAction {
        it.signInScreen()
    }

    private fun handleAuthenticationResults(isAuthenticated: Boolean) {
        if (isAuthenticated) {
            publishNavigationAction { router ->
                router.home()
            }
        }
    }
}
