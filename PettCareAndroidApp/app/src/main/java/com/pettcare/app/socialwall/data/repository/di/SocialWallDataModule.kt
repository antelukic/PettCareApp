package com.pettcare.app.socialwall.data.repository.di

import com.pettcare.app.socialwall.data.repository.SocialWallRepositoryImpl
import com.pettcare.app.socialwall.domain.repository.SocialWallRepository
import org.koin.dsl.module

val socialWallDataModule = module {
    factory<SocialWallRepository> { SocialWallRepositoryImpl(get(), get(), get()) }
}
