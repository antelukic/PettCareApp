package com.pettcare.app.auth.signin.domain

import com.pettcare.app.auth.login.domain.repository.AuthenticationRepository
import com.pettcare.app.auth.signin.domain.model.SignInData
import com.pettcare.app.auth.signin.domain.model.SignInUserParams
import com.pettcare.app.core.BaseResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SignInUser(
    private val authenticationRepository: AuthenticationRepository,
) {
    fun result(): Flow<SignInData> = authenticationRepository.signInResults().map(::mapSignInResponse)

    suspend fun request(params: SignInUserParams) = authenticationRepository.requestSignIn(params)

    private fun mapSignInResponse(data: BaseResponse<Boolean>): SignInData = when (data) {
        is BaseResponse.Loading -> SignInData(isSuccess = false, errorType = null, isLoading = true)
        is BaseResponse.Error -> SignInData(isSuccess = false, errorType = data, isLoading = false)
        is BaseResponse.Success -> SignInData(isSuccess = data.data, errorType = null, isLoading = false)
    }
}
