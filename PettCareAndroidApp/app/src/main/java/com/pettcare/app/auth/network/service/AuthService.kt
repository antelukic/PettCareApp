package com.pettcare.app.auth.network.service

import com.pettcare.app.auth.network.model.request.LoginRequestApi
import com.pettcare.app.auth.network.model.request.SignInRequestApi
import com.pettcare.app.auth.network.model.response.LoginResponseApi
import com.pettcare.app.auth.network.model.response.SignInResponseApi
import com.pettcare.app.core.BaseApiResponse

interface AuthService {

    suspend fun login(params: LoginRequestApi): BaseApiResponse<LoginResponseApi>?

    suspend fun signIn(params: SignInRequestApi): BaseApiResponse<SignInResponseApi>?

    suspend fun authenticate(token: String): BaseApiResponse<Boolean>?
}
