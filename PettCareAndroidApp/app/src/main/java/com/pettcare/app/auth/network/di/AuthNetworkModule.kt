package com.pettcare.app.auth.network.di

import com.pettcare.app.auth.network.service.AuthService
import com.pettcare.app.auth.network.service.AuthServiceImpl
import org.koin.dsl.module

val authNetworkModule = module {
    factory<AuthService> { AuthServiceImpl(get()) }
}
