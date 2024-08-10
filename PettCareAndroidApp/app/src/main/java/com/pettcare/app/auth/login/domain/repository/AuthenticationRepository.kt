package com.pettcare.app.auth.login.domain.repository

import com.pettcare.app.auth.login.domain.model.LoginRequestParams
import com.pettcare.app.auth.signin.domain.model.SignInUserParams
import com.pettcare.app.core.BaseResponse
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {

    fun logInResults(): Flow<BaseResponse<Boolean>>

    suspend fun requestLogIn(params: LoginRequestParams)

    fun signInResults(): Flow<BaseResponse<Boolean>>

    suspend fun requestSignIn(params: SignInUserParams)

    suspend fun authenticateUser()

    fun authenticateResults(): Flow<Boolean>
}
