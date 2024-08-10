package com.pettcare.app.auth.network.service

import com.pettcare.app.auth.network.model.request.LoginRequestApi
import com.pettcare.app.auth.network.model.request.SignInRequestApi
import com.pettcare.app.auth.network.model.response.LoginResponseApi
import com.pettcare.app.auth.network.model.response.SignInResponseApi
import com.pettcare.app.core.BaseApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url

class AuthServiceImpl(
    private val client: HttpClient,
) : AuthService {

    override suspend fun authenticate(token: String): BaseApiResponse<Boolean>? = runCatching {
        client.get {
            url(AUTHENTICATE_ROUTE)
            header("Authorization", "Bearer $token")
        }.body() as BaseApiResponse<Boolean>
    }.onFailure {
        it.printStackTrace()
    }.getOrNull()

    override suspend fun login(params: LoginRequestApi): BaseApiResponse<LoginResponseApi>? = runCatching {
        client.post {
            url(LOGIN_ROUTE)
            setBody(params)
        }.body() as BaseApiResponse<LoginResponseApi>
    }.onFailure {
        it.printStackTrace()
    }.getOrNull()

    override suspend fun signIn(params: SignInRequestApi): BaseApiResponse<SignInResponseApi>? =
        runCatching {
            client.post {
                url(SIGN_IN_ROUTE)
                setBody(params)
            }.body() as BaseApiResponse<SignInResponseApi>
        }.onFailure {
            it.printStackTrace()
        }.getOrNull()

    companion object {
        private const val AUTHENTICATE_ROUTE = "/authenticate"
        private const val SIGN_IN_ROUTE = "/register"
        private const val LOGIN_ROUTE = "/login"
    }
}
