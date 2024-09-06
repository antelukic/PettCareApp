package com.pettcare.app.auth.network.service

import com.pettcare.app.BASE_URL
import com.pettcare.app.auth.network.model.request.LoginRequestApi
import com.pettcare.app.auth.network.model.request.SignInRequestApi
import com.pettcare.app.auth.network.model.response.LoginResponseApi
import com.pettcare.app.auth.network.model.response.SignInResponseApi
import com.pettcare.app.core.BaseApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post

class AuthServiceImpl(
    private val client: HttpClient,
) : AuthService {

    override suspend fun authenticate(token: String): BaseApiResponse<Boolean>? = runCatching {
        client.get<BaseApiResponse<Boolean>>(BASE_URL + AUTHENTICATE_ROUTE) {
            header("Authorization", "Bearer $token")
        }
    }.onFailure {
        it.printStackTrace()
    }.getOrNull()

    override suspend fun login(params: LoginRequestApi): BaseApiResponse<LoginResponseApi>? = runCatching {
        client.post<BaseApiResponse<LoginResponseApi>>(BASE_URL + LOGIN_ROUTE) {
            body = params
        }
    }.onFailure {
        it.printStackTrace()
    }.getOrNull()

    override suspend fun signIn(params: SignInRequestApi): BaseApiResponse<SignInResponseApi>? =
        runCatching {
            client.post<BaseApiResponse<SignInResponseApi>>(BASE_URL + SIGN_IN_ROUTE) {
                body = params
            }
        }.onFailure {
            it.printStackTrace()
        }.getOrNull()

    companion object {
        private const val AUTHENTICATE_ROUTE = "/authenticate"
        private const val SIGN_IN_ROUTE = "/register"
        private const val LOGIN_ROUTE = "/login"
    }
}
