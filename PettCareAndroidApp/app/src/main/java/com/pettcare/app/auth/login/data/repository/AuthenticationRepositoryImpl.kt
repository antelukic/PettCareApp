package com.pettcare.app.auth.login.data.repository

import com.pettcare.app.auth.login.domain.model.LoginRequestParams
import com.pettcare.app.auth.login.domain.repository.AuthenticationRepository
import com.pettcare.app.core.BaseResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class AuthenticationRepositoryImpl : AuthenticationRepository {

    private val loginParamPublisher = MutableSharedFlow<LoginRequestParams>(replay = 1)

    override fun logInResults(): Flow<BaseResponse<Boolean>> = loginParamPublisher
        .onEach {
            // Implement once api is ready
        }
        .debounce(DEBOUNCE)
        .mapLatest { BaseResponse.Success(true) }
        .onStart { BaseResponse.Loading }

    override suspend fun requestLogIn(params: LoginRequestParams) {
        loginParamPublisher.emit(params)
    }

    companion object {
        private const val DEBOUNCE = 1000L
    }
}
