package com.pettcare.app.home.domain.di

import com.pettcare.app.home.domain.GetHomeData
import org.koin.dsl.module

val homeDomainModule = module {
    single { GetHomeData(get()) }
}
