package com.pettcare.app.auth.signin.network

interface Storage {

    suspend fun uploadFile(bytes: ByteArray, path: FirebaseStorage.Companion.PATHS, id: String): String
}
