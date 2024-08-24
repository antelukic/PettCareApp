package com.pettcare.app.auth.login.data.mapper

import com.pettcare.app.auth.login.domain.model.LoginRequestParams
import com.pettcare.app.auth.network.model.request.LoginRequestApi

fun LoginRequestParams.toLoginRequestApi() = LoginRequestApi(
    email = email,
    password = password,
)
