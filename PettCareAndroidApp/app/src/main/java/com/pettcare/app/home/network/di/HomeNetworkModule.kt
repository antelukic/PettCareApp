package com.pettcare.app.home.network.di

import com.pettcare.app.home.network.CarePostProfileApi
import com.pettcare.app.home.network.CarePostProfileApiImpl
import org.koin.dsl.module

val homeNetworkModule = module {
    single<CarePostProfileApi> { CarePostProfileApiImpl() }
}
