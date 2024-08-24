@file:Suppress("ImportOrdering", "ktlint:import-ordering")

package com.pettcare.app.create.data.repository

import android.content.Context
import android.net.Uri
import com.pettcare.app.auth.signin.network.FirebaseStorage
import com.pettcare.app.auth.signin.network.Storage
import com.pettcare.app.core.BaseResponse
import com.pettcare.app.create.data.mappers.toApi
import com.pettcare.app.create.domain.model.CarePostParams
import com.pettcare.app.create.domain.model.SocialPostParams
import com.pettcare.app.create.domain.repository.CreatePostRepository
import com.pettcare.app.create.network.CreatePostService
import com.pettcare.app.create.network.model.CreateCarePostRequestApi
import com.pettcare.app.create.network.model.CreateSocialPostRequestApi
import java.util.UUID
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.mapLatest

@OptIn(ExperimentalCoroutinesApi::class)
class CreatePostRepositoryImpl(
    private val createPostService: CreatePostService,
    private val storage: Storage,
    private val context: Context,
) : CreatePostRepository {

    private val carePostRequestPublisher = MutableSharedFlow<CreateCarePostRequestApi>(replay = 1)
    private val socialPostRequestPublisher = MutableSharedFlow<CreateSocialPostRequestApi>(replay = 1)

    override suspend fun createCarePost(params: CarePostParams) {
        val imageId = UUID.randomUUID().toString()
        val imageUrl = params.photo?.let { uploadPhoto(it, FirebaseStorage.Companion.PATHS.CARE_POST, imageId) }
        carePostRequestPublisher.emit(params.toApi(imageUrl, imageId))
    }

    override suspend fun createSocialPost(params: SocialPostParams) {
        val imageId = UUID.randomUUID().toString()
        val imageUrl = params.photoUrl?.let { uploadPhoto(it, FirebaseStorage.Companion.PATHS.CARE_POST, imageId) }
        socialPostRequestPublisher.emit(params.toApi(imageUrl, imageId))
    }

    override fun socialPostResults(): Flow<BaseResponse<Boolean>> = socialPostRequestPublisher
        .mapLatest {
            val response = createPostService.createSocialPost(it)
            if (response?.data != null) {
                BaseResponse.Success(true)
            } else {
                BaseResponse.Error.Network
            }
        }

    override fun carePostResults(): Flow<BaseResponse<Boolean>> = carePostRequestPublisher
        .mapLatest {
            val response = createPostService.createCarePost(it)
            if (response?.data != null) {
                BaseResponse.Success(true)
            } else {
                BaseResponse.Error.Network
            }
        }

    private suspend fun uploadPhoto(
        photo: Uri,
        paths: FirebaseStorage.Companion.PATHS,
        imageId: String,
    ): String? = readBytes(photo)?.let { byteArray ->
        storage.uploadFile(
            bytes = byteArray,
            path = paths,
            id = imageId,
        )
    }

    private fun readBytes(uri: Uri): ByteArray? =
        context.contentResolver.openInputStream(uri)?.use { it.buffered().readBytes() }
}
