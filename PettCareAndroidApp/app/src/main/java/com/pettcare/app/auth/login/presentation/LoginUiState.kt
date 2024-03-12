package com.pettcare.app.auth.login.presentation

import com.pettcare.app.auth.login.presentation.translations.LoginTranslations
import com.pettcare.app.extensions.EMPTY

data class LoginUiState(
    val title: String = EMPTY,
    val email: String = EMPTY,
    val password: String = EMPTY,
    val btnContinueTxt: String = EMPTY,
    val emailPlaceholder: String = EMPTY,
    val passwordPlaceholder: String = EMPTY,
    val passwordAccessibility: String = EMPTY,
    val isLoginButtonEnabled: Boolean = true,
    val isError: Boolean = false,
    val errorMessage: String = EMPTY,
) {
    constructor(loginTranslations: LoginTranslations) : this(
        title = loginTranslations.title(),
        btnContinueTxt = loginTranslations.btnContinueTxt(),
        passwordPlaceholder = loginTranslations.passwordPlaceholder(),
        passwordAccessibility = loginTranslations.passwordAccessibility(),
        emailPlaceholder = loginTranslations.emailPlaceholder(),
    )
}
