package com.pettcare.app.auth.signin.domain.model

import android.net.Uri

data class SignInUserParams(
    val name: String,
    val surname: String,
    val password: String,
    val email: String,
    val gender: String,
    val dateOfBirth: String,
    val photo: Uri?,
)
