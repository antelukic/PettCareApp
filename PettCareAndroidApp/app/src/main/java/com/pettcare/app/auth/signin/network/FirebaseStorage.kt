package com.pettcare.app.auth.signin.network

import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await

class FirebaseStorage(
    private val storageReference: StorageReference,
) : Storage {

    override suspend fun uploadFile(bytes: ByteArray, path: PATHS, id: String): String =
        storageReference.child(path.path + id).putBytes(bytes).await().storage.downloadUrl.await().toString()

    companion object {

        enum class PATHS(val path: String) {
            AVATAR("images/avatars/"),
        }
    }
}
