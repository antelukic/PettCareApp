package com.pettcare.app.socialwall.network.di

import com.pettcare.app.socialwall.network.SocialWallApi
import com.pettcare.app.socialwall.network.SocialWallApiImpl
import org.koin.dsl.module

val socialWallNetworkModule = module {
    factory<SocialWallApi> { SocialWallApiImpl() }
}
