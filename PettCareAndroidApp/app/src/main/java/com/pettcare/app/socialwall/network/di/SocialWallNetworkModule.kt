package com.pettcare.app.socialwall.network.di

import com.pettcare.app.socialwall.network.SocialWallService
import com.pettcare.app.socialwall.network.SocialWallServiceImpl
import org.koin.dsl.module

val socialWallNetworkModule = module {
    factory<SocialWallService> { SocialWallServiceImpl(get(), get()) }
}
