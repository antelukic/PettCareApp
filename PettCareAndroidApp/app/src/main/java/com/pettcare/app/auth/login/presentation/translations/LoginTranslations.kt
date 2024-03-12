package com.pettcare.app.auth.login.presentation.translations

import android.content.Context
import com.pettcare.app.R
import com.pettcare.app.core.BaseTranslations

class LoginTranslations(
    context: Context,
) : BaseTranslations(context) {

    fun unknownErrorMessage() = resources.getString(R.string.unkown_error_message)

    fun networkErrorMessage() = resources.getString(R.string.network_error_message)

    fun title() = resources.getString(R.string.log_in_title)

    fun btnContinueTxt() = resources.getString(R.string.log_in_button_text)

    fun passwordAccessibility() = resources.getString(R.string.change_password_visibility_accessibility)

    fun emailPlaceholder() = resources.getString(R.string.email_placeholder)

    fun passwordPlaceholder() = resources.getString(R.string.password_placeholder)
}
