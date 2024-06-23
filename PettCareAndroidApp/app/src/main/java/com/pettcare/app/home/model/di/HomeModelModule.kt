package com.pettcare.app.home.model.di

import com.pettcare.app.home.domain.HomeRepository
import com.pettcare.app.home.model.HomeRepositoryImpl
import org.koin.dsl.module

val homeModelModule = module {
    single<HomeRepository> { HomeRepositoryImpl(get()) }
}
