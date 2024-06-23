package com.pettcare.app.auth.login.domain.model

import com.pettcare.app.core.BaseResponse

data class LoginData(
    val isSuccess: Boolean,
    val errorType: BaseResponse.Error?,
    val isLoading: Boolean,
)
