package com.pettcare.app.auth.signin.network.di

import com.google.firebase.storage.FirebaseStorage
import com.pettcare.app.auth.signin.network.Storage
import org.koin.dsl.bind
import org.koin.dsl.module

val firebaseModule = module {

    single { FirebaseStorage.getInstance().reference.storage.reference }
    single { com.pettcare.app.auth.signin.network.FirebaseStorage(get()) } bind Storage::class
}
