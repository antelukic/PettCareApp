package com.pettcare.app.profile.network.di

import com.pettcare.app.profile.network.ApiProfile
import com.pettcare.app.profile.network.ApiProfileImpl
import org.koin.dsl.module

val profileNetworkModule = module {
    factory<ApiProfile> { ApiProfileImpl() }
}
