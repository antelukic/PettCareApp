package com.pettcare.app.home.network.di

import com.pettcare.app.home.network.service.CarePostService
import com.pettcare.app.home.network.service.CarePostServiceImpl
import org.koin.dsl.module

val homeNetworkModule = module {
    single<CarePostService> { CarePostServiceImpl(get(), get()) }
}
