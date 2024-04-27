@file:Suppress("ImportOrdering", "ktlint:import-ordering")

package com.pettcare.app.auth.login.data.repository

import android.content.Context
import android.net.Uri
import com.pettcare.app.auth.login.domain.model.LoginRequestParams
import com.pettcare.app.auth.login.domain.repository.AuthenticationRepository
import com.pettcare.app.auth.signin.data.model.SignInUserParams as DataSignInUserParams
import com.pettcare.app.auth.signin.data.model.toRepoSignInUserParams
import com.pettcare.app.auth.signin.domain.model.SignInUserParams
import com.pettcare.app.auth.signin.network.FirebaseStorage
import com.pettcare.app.auth.signin.network.Storage
import com.pettcare.app.core.BaseResponse
import java.util.UUID
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class AuthenticationRepositoryImpl(
    private val storage: Storage,
    private val context: Context,
) : AuthenticationRepository {

    private val loginParamPublisher = MutableSharedFlow<LoginRequestParams>(replay = 1)
    private val signInParamPublisher = MutableSharedFlow<DataSignInUserParams>()

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

    override suspend fun requestSignIn(params: SignInUserParams) {
        val imageId = UUID.randomUUID().toString()
        val imageUrl = getImageUrl(params.photo, imageId)
        signInParamPublisher.emit(params.toRepoSignInUserParams(imageUrl, imageId))
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
        .onEach {
            // Implement once api is ready
        }
        .debounce(DEBOUNCE)
        .mapLatest { BaseResponse.Success(true) }
        .onStart { BaseResponse.Loading }

    companion object {
        // Remove when api is ready
        private const val DEBOUNCE = 1000L
    }
}
