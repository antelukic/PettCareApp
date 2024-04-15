package com.pettcare.app.auth.login.data.di

import com.pettcare.app.auth.login.data.repository.AuthenticationRepositoryImpl
import com.pettcare.app.auth.login.domain.repository.AuthenticationRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val authDataModule = module {

    factory<AuthenticationRepository> {
        AuthenticationRepositoryImpl(get(), androidContext())
    }
}
