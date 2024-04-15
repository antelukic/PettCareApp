package com.pettcare.app.welcome

import com.pettcare.app.welcome.translations.WelcomeResources

data class WelcomeScreenUIState(
    val welcomeMessage: String = "",
    val signInText: String = "",
    val logInText: String = "",
) {
    constructor(welcomeTranslations: WelcomeResources) : this(
        welcomeMessage = welcomeTranslations.welcomeMessage(),
        signInText = welcomeTranslations.signInMessage(),
        logInText = welcomeTranslations.logInMessage(),
    )
}
