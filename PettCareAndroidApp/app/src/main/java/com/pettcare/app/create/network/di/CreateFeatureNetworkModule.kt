package com.pettcare.app.create.network.di

import com.google.android.libraries.places.api.Places
import com.pettcare.app.create.network.CreatePostService
import com.pettcare.app.create.network.CreatePostServiceImpl
import com.pettcare.app.create.network.LocationService
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val createFeatureNetworkModule = module {
    factory<CreatePostService> { CreatePostServiceImpl(get(), get()) }
    factory<LocationService> { LocationService(Places.createClient(androidContext())) }
}
