package com.pettcare.app.profile.network.di

import com.pettcare.app.profile.network.UserService
import com.pettcare.app.profile.network.UserServiceImpl
import org.koin.dsl.module

val profileNetworkModule = module {
    factory<UserService> { UserServiceImpl(get(), get()) }
}
