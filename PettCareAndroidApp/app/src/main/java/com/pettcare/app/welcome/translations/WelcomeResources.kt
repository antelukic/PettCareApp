package com.pettcare.app.welcome.translations

import android.content.Context
import com.pettcare.app.R

class WelcomeResources(
    private val context: Context,
) {

    private val resources by lazy {
        context.resources
    }

    fun welcomeMessage() = resources.getString(R.string.welcome_screen_message)

    fun signInMessage() = resources.getString(R.string.welcome_sign_in_message)

    fun logInMessage() = resources.getString(R.string.welcome_log_in_message)
}
