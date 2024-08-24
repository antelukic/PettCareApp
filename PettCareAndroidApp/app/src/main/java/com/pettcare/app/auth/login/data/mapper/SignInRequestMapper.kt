package com.pettcare.app.auth.login.data.mapper

import com.pettcare.app.auth.network.model.request.SignInRequestApi
import com.pettcare.app.auth.signin.data.model.SignInUserParams

fun SignInUserParams.toApi() = SignInRequestApi(
    fullName = "$name $surname",
    avatarUrl = photoUrl,
    email = email,
    password = password,
    imageId = imageId.orEmpty(),
    gender = gender,
    dateOfBirth = dateOfBirth,
)
