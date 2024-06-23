package com.pettcare.app.auth.signin.presentation.translation

import android.content.Context
import com.pettcare.app.R
import com.pettcare.app.core.BaseTranslations

@Suppress("TooManyFunctions")
class SignInTranslations(context: Context) : BaseTranslations(context) {

    fun title() = resources.getString(R.string.registration_title_label)

    fun nameLabel() = resources.getString(R.string.registration_name_label)

    fun namePlaceholder() = resources.getString(R.string.registration_name_placeholder)

    fun emptyFieldError() = resources.getString(R.string.registration_empty_field_error)

    fun emailPlaceholder() = resources.getString(R.string.email_placeholder)

    fun passwordPlaceholder() = resources.getString(R.string.password_placeholder)

    fun passwordNoMatchError() = resources.getString(R.string.registration_password_no_match_error)

    fun repeatPasswordLabel() = resources.getString(R.string.registration_repeat_password_label)

    fun surnameLabel() = resources.getString(R.string.registration_surname_label)

    fun surnamePlaceholder() = resources.getString(R.string.registration_surname_placeholder)

    fun emailLabel() = resources.getString(R.string.registration_email_label)

    fun passwordLabel() = resources.getString(R.string.registration_password_label)

    fun ageLabel() = resources.getString(R.string.registration_age_label)

    fun agePlaceholder() = resources.getString(R.string.registration_age_placeholder)

    fun genderLabel() = resources.getString(R.string.registration_gender_label)

    fun genders() = resources.getStringArray(R.array.genders)

    fun btnNextTxt() = resources.getString(R.string.registration_next_text)

    fun btnNextTxtLastPage() = resources.getString(R.string.registration_submit_text)

    fun btnPreviousTxt() = resources.getString(R.string.registration_previous_text)

    fun btnDatePickerConfirmTxt() = resources.getString(R.string.registration_date_picker_ok)

    fun unknownErrorMessage() = resources.getString(R.string.unkown_error_message)

    fun networkErrorMessage() = resources.getString(R.string.network_error_message)
}
