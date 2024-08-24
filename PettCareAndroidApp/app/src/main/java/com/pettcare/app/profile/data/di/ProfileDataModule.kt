package com.pettcare.app.profile.data.di

import com.pettcare.app.profile.data.repository.ProfileRepositoryImpl
import com.pettcare.app.profile.domain.repository.ProfileRepository
import org.koin.dsl.module

val profileDataModule = module {
    factory<ProfileRepository> { ProfileRepositoryImpl(get(), get(), get()) }
}
