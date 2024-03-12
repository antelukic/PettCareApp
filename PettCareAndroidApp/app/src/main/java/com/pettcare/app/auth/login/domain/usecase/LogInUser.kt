package com.pettcare.app.auth.login.domain.usecase

import com.pettcare.app.auth.login.domain.model.LoginData
import com.pettcare.app.auth.login.domain.model.LoginRequestParams
import com.pettcare.app.auth.login.domain.repository.AuthenticationRepository
import com.pettcare.app.core.BaseResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LogInUser(
    private val authenticationRepository: AuthenticationRepository,
) {

    fun result(): Flow<LoginData> = authenticationRepository.logInResults().map(::mapLoginResponse)

    suspend fun request(email: String, password: String) = authenticationRepository.requestLogIn(
        LoginRequestParams(email, password),
    )

    private fun mapLoginResponse(data: BaseResponse<Boolean>): LoginData = when (data) {
        is BaseResponse.Loading -> LoginData(isSuccess = false, errorType = null, isLoading = true)
        is BaseResponse.Error -> LoginData(isSuccess = false, errorType = data, isLoading = false)
        is BaseResponse.Success -> LoginData(isSuccess = true, errorType = null, isLoading = false)
    }
}
