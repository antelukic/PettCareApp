package com.pettcare.app.auth.signin.data.model

import com.pettcare.app.auth.signin.domain.model.SignInUserParams as DomainUserParams

data class SignInUserParams(
    val name: String,
    val surname: String,
    val password: String,
    val email: String,
    val dateOfBirth: String,
    val gender: String,
    val photoUrl: String?,
    val imageId: String?,
)

internal fun DomainUserParams.toRepoSignInUserParams(imageUrl: String?, imageId: String) = SignInUserParams(
    name = name,
    surname = surname,
    password = password,
    email = email,
    dateOfBirth = dateOfBirth,
    gender = gender,
    photoUrl = imageUrl,
    imageId = imageId,
)
