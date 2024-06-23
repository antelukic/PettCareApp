package com.pettcare.app.create.data.di

import com.pettcare.app.create.data.repository.CreatePostRepositoryImpl
import com.pettcare.app.create.data.repository.LocationRepositoryImpl
import com.pettcare.app.create.domain.repository.CreatePostRepository
import com.pettcare.app.create.domain.repository.LocationRepository
import org.koin.dsl.module

val createFeatureDataModule = module {
    factory<CreatePostRepository> { CreatePostRepositoryImpl(get(), get(), get()) }
    factory<LocationRepository> { LocationRepositoryImpl(get()) }
}
