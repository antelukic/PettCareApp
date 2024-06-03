package com.pettcare.app.create.network.di

import com.google.android.libraries.places.api.Places
import com.pettcare.app.create.network.ApiCreatePost
import com.pettcare.app.create.network.ApiCreatePostImpl
import com.pettcare.app.create.network.LocationService
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val createFeatureNetworkModule = module {
    factory<ApiCreatePost> { ApiCreatePostImpl() }
    factory<LocationService> { LocationService(Places.createClient(androidContext())) }
}
