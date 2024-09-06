@file:Suppress("ImportOrdering", "ktlint:import-ordering")

package com.pettcare.app.auth.login.data.repository

import android.content.Context
import android.net.Uri
import com.pettcare.app.auth.login.data.mapper.toApi
import com.pettcare.app.auth.login.data.mapper.toLoginRequestApi
import com.pettcare.app.auth.login.domain.model.LoginRequestParams
import com.pettcare.app.auth.login.domain.repository.AuthenticationRepository
import com.pettcare.app.auth.network.service.AuthService
import com.pettcare.app.auth.signin.data.model.toRepoSignInUserParams
import com.pettcare.app.auth.signin.domain.model.SignInUserParams
import com.pettcare.app.auth.signin.network.FirebaseStorage
import com.pettcare.app.auth.signin.network.Storage
import com.pettcare.app.core.BaseResponse
import com.pettcare.app.sharedprefs.SharedPreferences
import java.util.UUID
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onStart
import com.pettcare.app.auth.signin.data.model.SignInUserParams as DataSignInUserParams

@OptIn(ExperimentalCoroutinesApi::class)
class AuthenticationRepositoryImpl(
    private val storage: Storage,
    private val context: Context,
    private val authService: AuthService,
    private val sharedPreferences: SharedPreferences,
) : AuthenticationRepository {

    private val loginParamPublisher = MutableSharedFlow<LoginRequestParams>(replay = 1)
    private val signInParamPublisher = MutableSharedFlow<DataSignInUserParams>(replay = 1)
    private val authenticateUserPublisher = MutableSharedFlow<Unit>(replay = 1)

    override fun logInResults(): Flow<BaseResponse<Boolean>> = loginParamPublisher
        .mapLatest {
            val response = authService.login(it.toLoginRequestApi())
            if (response != null) {
                storeUserInfo(response.data?.id, response.data?.authToken)
                BaseResponse.Success(true)
            } else {
                BaseResponse.Error.Network
            }
        }
        .onStart { BaseResponse.Loading }

    override suspend fun requestLogIn(params: LoginRequestParams) {
        loginParamPublisher.emit(params)
    }

    override suspend fun requestSignIn(params: SignInUserParams) {
        val imageId = UUID.randomUUID().toString()
        val imageUrl = getImageUrl(params.photo, imageId)
        signInParamPublisher.emit(params.toRepoSignInUserParams(imageUrl, imageId))
    }

    override suspend fun authenticateUser() {
        authenticateUserPublisher.emit(Unit)
    }

    override fun authenticateResults(): Flow<Boolean> = authenticateUserPublisher
        .mapLatest {
            val token = sharedPreferences.getString(SharedPreferences.TOKEN_KEY) ?: return@mapLatest false
            val response = authService.authenticate(token)
            response?.data != null
        }

    private suspend fun getImageUrl(photo: Uri?, imageId: String): String? = if (photo != null) {
        readBytes(photo)?.let { byteArray ->
            storage.uploadFile(
                bytes = byteArray,
                path = FirebaseStorage.Companion.PATHS.AVATAR,
                id = imageId,
            )
        }
    } else {
        null
    }

    private fun readBytes(uri: Uri): ByteArray? =
        context.contentResolver.openInputStream(uri)?.use { it.buffered().readBytes() }

    override fun signInResults(): Flow<BaseResponse<Boolean>> = signInParamPublisher
        .mapLatest(::signInUser)
        .onStart { BaseResponse.Loading }

    private suspend fun signInUser(params: DataSignInUserParams): BaseResponse<Boolean> {
        val response = authService.signIn(params.toApi())
        return if (response != null) {
            storeUserInfo(response.data?.id, response.data?.authToken)
            BaseResponse.Success(response.data != null)
        } else {
            BaseResponse.Error.Network
        }
    }

    private fun storeUserInfo(userId: String?, token: String?) {
        userId ?: return
        token ?: return
        sharedPreferences.storeString(SharedPreferences.ID_KEY, userId)
        sharedPreferences.storeString(SharedPreferences.TOKEN_KEY, token)
    }
}
