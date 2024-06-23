package com.pettcare.app.welcome

import com.pettcare.app.core.BaseViewModel
import com.pettcare.app.navigation.Router
import com.pettcare.app.welcome.translations.WelcomeResources

internal class WelcomeViewModel(
    welcomeResources: WelcomeResources,
    router: Router,
) : BaseViewModel<WelcomeScreenUIState>(router = router, WelcomeScreenUIState(welcomeResources)) {

    fun navigateToLogin() = publishNavigationAction {
        it.loginScreen()
    }

    fun navigateToSignIn() = publishNavigationAction {
        it.signInScreen()
    }
}
