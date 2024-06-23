package com.pettcare.app.auth.signin.presentation

import android.net.Uri
import com.pettcare.app.auth.signin.presentation.translation.SignInTranslations
import com.pettcare.app.extensions.EMPTY
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import java.util.UUID

data class SignInUiState(
    val title: String,
    val name: InputFieldData,
    val surname: InputFieldData,
    val email: InputFieldData,
    val password: InputFieldData,
    val repeatPassword: InputFieldData,
    val gender: InputFieldData,
    val age: InputFieldData,
    val btnNextText: String,
    val btnPreviousText: String,
    val isDatePickerVisible: Boolean,
    val datePickerConfirmButtonTxt: String,
    val isGendersBottomSheetVisible: Boolean,
    val photo: Uri?,
    val isLoading: Boolean,
    val errorMessage: String?,
) {
    constructor(signInTranslations: SignInTranslations) : this(
        title = signInTranslations.title(),
        name = InputFieldData(
            text = EMPTY,
            errorText = null,
            isError = false,
            label = signInTranslations.nameLabel(),
            placeholder = signInTranslations.namePlaceholder(),
        ),
        surname = InputFieldData(
            text = EMPTY,
            errorText = null,
            isError = false,
            label = signInTranslations.surnameLabel(),
            placeholder = signInTranslations.surnamePlaceholder(),
        ),
        email = InputFieldData(
            text = EMPTY,
            errorText = null,
            isError = false,
            label = signInTranslations.emailLabel(),
            placeholder = signInTranslations.emailPlaceholder(),
        ),
        password = InputFieldData(
            text = EMPTY,
            errorText = null,
            isError = false,
            label = signInTranslations.passwordLabel(),
            placeholder = signInTranslations.passwordPlaceholder(),
        ),
        repeatPassword = InputFieldData(
            text = EMPTY,
            errorText = null,
            isError = false,
            label = signInTranslations.repeatPasswordLabel(),
            placeholder = signInTranslations.passwordPlaceholder(),
        ),
        gender = InputFieldData(
            text = EMPTY,
            errorText = null,
            isError = false,
            label = signInTranslations.genderLabel(),
            placeholder = signInTranslations.genders().first(),
            texts = signInTranslations.genders().map { UUID.randomUUID().toString() to it }.toImmutableList(),
        ),
        age = InputFieldData(
            text = EMPTY,
            errorText = null,
            isError = false,
            label = signInTranslations.ageLabel(),
            placeholder = signInTranslations.agePlaceholder(),
        ),
        btnNextText = signInTranslations.btnNextTxt(),
        btnPreviousText = signInTranslations.btnPreviousTxt(),
        isDatePickerVisible = false,
        datePickerConfirmButtonTxt = signInTranslations.btnDatePickerConfirmTxt(),
        isGendersBottomSheetVisible = false,
        photo = null,
        isLoading = false,
        errorMessage = null,
    )

    fun updateRepeatPassword(value: String, signInTranslations: SignInTranslations): SignInUiState {
        val errorText = if (doPasswordsMatch(value)) {
            null
        } else {
            signInTranslations.passwordNoMatchError()
        }

        return copy(
            repeatPassword = repeatPassword.copy(
                text = value,
                isError = doPasswordsMatch(value).not(),
                errorText = errorText,
            ),
        )
    }

    private fun doPasswordsMatch(repeatPassword: String) = repeatPassword == password.text
}

data class InputFieldData(
    val text: String,
    val errorText: String?,
    val isError: Boolean,
    val label: String,
    val placeholder: String,
    val texts: ImmutableList<Pair<String, String>> = persistentListOf(),
)
