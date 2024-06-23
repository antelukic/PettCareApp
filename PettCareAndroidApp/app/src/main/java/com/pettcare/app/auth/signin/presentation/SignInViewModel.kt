package com.pettcare.app.auth.signin.presentation

import android.net.Uri
import com.pettcare.app.auth.signin.domain.GetDateFromMillis
import com.pettcare.app.auth.signin.domain.SignInUser
import com.pettcare.app.auth.signin.domain.model.SignInData
import com.pettcare.app.auth.signin.domain.model.SignInUserParams
import com.pettcare.app.auth.signin.presentation.translation.SignInTranslations
import com.pettcare.app.core.BaseResponse
import com.pettcare.app.core.BaseViewModel
import com.pettcare.app.extensions.EMPTY
import com.pettcare.app.navigation.Router
import kotlinx.coroutines.flow.map

class SignInViewModel(
    private val getDateFromMillis: GetDateFromMillis,
    private val signInTranslations: SignInTranslations,
    private val signInUser: SignInUser,
    router: Router,
) : BaseViewModel<SignInUiState>(router, SignInUiState(signInTranslations)) {

    init {
        launchInIO {
            signInUser.result().map(::mapSignInData)
        }
    }

    private fun mapSignInData(data: SignInData) {
        when {
            data.isSuccess -> publishNavigationAction {
                // navigate to main screen
            }

            data.isLoading -> updateUiState { state -> state.copy(isLoading = true) }
            data.errorType != null -> updateUiState { state ->
                state.copy(errorMessage = data.errorType.errorMessage())
            }
        }
    }

    fun onNameChanged(value: String) {
        updateUiState { state -> state.copy(name = state.name.copy(text = value)) }
    }

    fun onSurnameChanged(value: String) {
        updateUiState { state -> state.copy(surname = state.surname.copy(text = value)) }
    }

    fun onGenderSelected(valueId: String) {
        updateUiState { state ->
            state.copy(
                gender = state.gender.copy(
                    text = state.gender.texts.first { it.first == valueId }.second,
                ),
                isGendersBottomSheetVisible = false,
            )
        }
    }

    fun onTriggerGenderVisibility(isVisible: Boolean) {
        updateUiState { state -> state.copy(isGendersBottomSheetVisible = isVisible) }
    }

    fun onPhotoPicked(value: Uri?) {
        updateUiState { state -> state.copy(photo = value) }
    }

    fun onTriggerDatePickerVisibility(isVisible: Boolean) {
        updateUiState { state -> state.copy(isDatePickerVisible = isVisible) }
    }

    fun onAgeSelected(value: Long?) {
        if (value == null) return
        updateUiState { state ->
            state.copy(
                age = state.age.copy(text = getDateFromMillis(value)),
                isDatePickerVisible = false,
            )
        }
    }

    fun onPasswordChanged(value: String) {
        updateUiState { state -> state.copy(password = state.password.copy(text = value)) }
    }

    fun onRepeatPasswordChanged(value: String) {
        updateUiState { state -> state.updateRepeatPassword(value, signInTranslations) }
    }

    fun onEmailChanged(value: String) {
        updateUiState { state -> state.copy(email = state.email.copy(text = value)) }
    }

    fun onSubmit() {
        launchInIO {
            with(uiState.value) {
                validateFields()
                signInUser.request(
                    SignInUserParams(
                        name = name.text,
                        surname = surname.text,
                        password = password.text,
                        email = email.text,
                        gender = gender.text,
                        dateOfBirth = age.text,
                        photo = photo,
                    ),
                )
            }
        }
    }

    private fun SignInUiState.validateFields(): Boolean {
        return when {
            name.text.isBlank() -> {
                updateUiState { state ->
                    state.copy(name = state.name.copy(errorText = signInTranslations.emptyFieldError()))
                }
                false
            }

            surname.text.isBlank() -> {
                updateUiState { state ->
                    state.copy(name = state.surname.copy(errorText = signInTranslations.emptyFieldError()))
                }
                false
            }

            password.text.isBlank() -> {
                updateUiState { state ->
                    state.copy(name = state.password.copy(errorText = signInTranslations.emptyFieldError()))
                }
                false
            }

            email.text.isBlank() -> {
                updateUiState { state ->
                    state.copy(name = state.email.copy(errorText = signInTranslations.emptyFieldError()))
                }
                false
            }

            gender.text.isBlank() -> {
                updateUiState { state ->
                    state.copy(name = state.gender.copy(errorText = signInTranslations.emptyFieldError()))
                }
                false
            }

            age.text.isBlank() -> {
                updateUiState { state ->
                    state.copy(name = state.age.copy(errorText = signInTranslations.emptyFieldError()))
                }
                false
            }

            else -> true
        }
    }

    private fun BaseResponse.Error?.errorMessage() = when (this) {
        is BaseResponse.Error.Other -> signInTranslations.unknownErrorMessage()
        is BaseResponse.Error.Network -> signInTranslations.networkErrorMessage()
        else -> EMPTY
    }

    fun onPageUpdate(isLastPage: Boolean) {
        if (isLastPage) {
            updateUiState { state -> state.copy(btnNextText = signInTranslations.btnNextTxtLastPage()) }
        } else {
            updateUiState { state -> state.copy(btnNextText = signInTranslations.btnNextTxt()) }
        }
    }
}
