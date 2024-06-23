package com.pettcare.app.profile.domain.di

import com.pettcare.app.profile.domain.usecase.GetProfileData
import org.koin.dsl.module

val profileDomainModule = module {
    factory { GetProfileData(get()) }
}
