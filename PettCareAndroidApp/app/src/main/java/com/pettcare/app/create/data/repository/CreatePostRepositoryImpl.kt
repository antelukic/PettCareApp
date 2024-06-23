package com.pettcare.app.create.data.repository

import android.content.Context
import android.net.Uri
import com.pettcare.app.auth.signin.network.FirebaseStorage
import com.pettcare.app.auth.signin.network.Storage
import com.pettcare.app.create.data.mappers.toApi
import com.pettcare.app.create.domain.model.CarePostParams
import com.pettcare.app.create.domain.model.SocialPostParams
import com.pettcare.app.create.domain.repository.CreatePostRepository
import com.pettcare.app.create.network.ApiCreatePost
import java.util.UUID

class CreatePostRepositoryImpl(
    private val apiCreatePost: ApiCreatePost,
    private val storage: Storage,
    private val context: Context,
) : CreatePostRepository {

    override suspend fun createCarePost(params: CarePostParams) {
        val imageId = UUID.randomUUID().toString()
        val imageUrl = params.photo?.let { uploadPhoto(it, FirebaseStorage.Companion.PATHS.CARE_POST, imageId) }
        apiCreatePost.createCarePost(params.toApi(imageUrl, imageId))
    }

    override suspend fun createSocialPost(params: SocialPostParams) {
        val imageId = UUID.randomUUID().toString()
        val imageUrl = params.photoUrl?.let { uploadPhoto(it, FirebaseStorage.Companion.PATHS.CARE_POST, imageId) }
        apiCreatePost.createSocialPost(params.toApi(imageUrl, imageId))
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
