package com.pettcare.app.auth.login.domain

import com.pettcare.app.core.BaseResponse
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun logInUser(): Flow<BaseResponse<Boolean>>
}
