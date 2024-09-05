package com.pettcare.app.profile.domain.di

import com.pettcare.app.profile.domain.usecase.GetProfileData
import com.pettcare.app.profile.domain.usecase.SignOut
import org.koin.dsl.module

val profileDomainModule = module {
    factory { GetProfileData(get()) }
    factory { SignOut(get()) }
}
