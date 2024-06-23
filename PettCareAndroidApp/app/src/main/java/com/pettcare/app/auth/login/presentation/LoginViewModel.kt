package com.pettcare.app.auth.login.presentation

import com.pettcare.app.auth.login.domain.model.LoginData
import com.pettcare.app.auth.login.domain.usecase.LogInUser
import com.pettcare.app.auth.login.presentation.translations.LoginTranslations
import com.pettcare.app.core.BaseResponse
import com.pettcare.app.core.BaseViewModel
import com.pettcare.app.extensions.EMPTY
import com.pettcare.app.navigation.Router

class LoginViewModel(
    private val logInUser: LogInUser,
    private val loginTranslations: LoginTranslations,
    router: Router,
) : BaseViewModel<LoginUiState>(router, LoginUiState(loginTranslations)) {

    init {
        launchInMain {
            logInUser.result().collect(::handleLogInResult)
        }
    }

    fun updatePassword(password: String) {
        updateUiState { state ->
            state.copy(password = password)
        }
    }

    fun updateEmail(email: String) {
        updateUiState { state ->
            state.copy(email = email)
        }
    }

    fun signIn() {
        launchInIO {
            updateUiState { state ->
                state.copy(isLoginButtonEnabled = false)
            }
            logInUser.request(uiState.value.email, uiState.value.password)
        }
    }

    private fun handleLogInResult(data: LoginData) {
        updateUiState { state ->
            state.copy(
                isError = data.errorType != null,
                errorMessage = data.errorType.errorMessage(),
                isLoginButtonEnabled = data.errorType != null,
            )
        }
    }

    private fun BaseResponse.Error?.errorMessage() = when (this) {
        is BaseResponse.Error.Other -> loginTranslations.unknownErrorMessage()
        is BaseResponse.Error.Network -> loginTranslations.networkErrorMessage()
        else -> EMPTY
    }
}
