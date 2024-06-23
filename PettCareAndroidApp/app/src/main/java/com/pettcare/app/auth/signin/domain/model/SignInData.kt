package com.pettcare.app.auth.signin.domain.model

import com.pettcare.app.core.BaseResponse

data class SignInData(
    val isSuccess: Boolean,
    val errorType: BaseResponse.Error?,
    val isLoading: Boolean,
)
